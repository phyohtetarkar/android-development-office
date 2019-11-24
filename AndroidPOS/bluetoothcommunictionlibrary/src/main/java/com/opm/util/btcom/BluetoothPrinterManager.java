package com.opm.util.btcom;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothPrinterManager {

    private static final String TAG = "BT_PRINT_MGR";
    private static final String BT_UUID = "00001101-0000-1000-8000-00805f9b34fb";

    private BluetoothConnectThread connectThread;
    private BluetoothConnectedThread connectedThread;

    private static BluetoothPrinterManager INSTANCE;
    private Handler mHandler;
    private String printerMacAddress;
    private Runnable pendingPrint;

    public interface MessageConstants {
        int PRINT_SUCCESS = 0;
        int COVER_OPEN_ERROR = 1;
        int LABEL_EMPTY = 2;
        int COMMAND_SYNTAX_ERROR = 3;
        int PAPER_JAM = 4;
        int UNKNOWN_ERROR = 5;
        int COMMUNICATION_ERROR = 6;
    }

    public static BluetoothPrinterManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BluetoothPrinterManager();
        }
        return INSTANCE;
    }

    public static BluetoothPrinterManager getInstance(String macAddress) {
        return new BluetoothPrinterManager(macAddress);
    }

    private BluetoothPrinterManager() {
    }

    private BluetoothPrinterManager(String macAddress) {
        this.printerMacAddress = macAddress;
        init();
    }

    public void init() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            throw new BluetoothNotSupportException();
        }

        checkBluetoothOpen();

        startConnect();
    }

    public void print(byte[] bytes) {
        /*if (connectedThread != null && connectThread.isAlive()) {
            throw new PrintingInProgressException();
        }*/

        checkBluetoothOpen();

        if (connectThread == null) {
            startConnect();
            pendingPrint = () -> connectedThread.write(bytes);
        } else {
            if (connectedThread == null) {
                Log.d(TAG, "Pending print.");
                pendingPrint = () -> connectedThread.write(bytes);
            } else {
                Log.d(TAG, "Not pending.");
                connectedThread.write(bytes);
            }
        }
    }

    public void close() {
        if (connectedThread != null && connectedThread.isAlive()) {
            connectedThread.interrupt();
        }

        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
            connectedThread = null;
        }
    }

    public boolean isConnected() {
        return connectThread != null && connectThread.isAlive();
    }

    public void setPrinterMacAddress(String macAddress) {
        String old = printerMacAddress;
        this.printerMacAddress = macAddress;

        if (macAddress != null && !macAddress.equals(old)) {
            init();
        }
    }

    /**
     * Handler for sending message to Main UI
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.mHandler = handler;
    }

    private void startConnect() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> btList = bluetoothAdapter.getBondedDevices();

        if (printerMacAddress == null || printerMacAddress.isEmpty()) {
            throw new NoPrinterSelectedException();
        }

        BluetoothDevice device = getDevice(btList, printerMacAddress);
        connect(device);
    }

    private void checkBluetoothOpen() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            close();
            throw new BluetoothOffException();
        }
    }

    private BluetoothDevice getDevice(Set<BluetoothDevice> btList, String macAddress) {
        for (BluetoothDevice device : btList) {
            if (device.getAddress().equals(macAddress)) {
                return device;
            }
        }

        return null;
    }

    private void connect(BluetoothDevice device) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.cancelDiscovery();

        close();

        connectThread = new BluetoothConnectThread(device);
        connectThread.start();
    }

    /**
     * Create Bluetooth RFCOMM communication thread
     */
    private class BluetoothConnectThread extends Thread {
        private final BluetoothSocket mSocket;

        BluetoothConnectThread(BluetoothDevice device) {
            BluetoothSocket tmpSocket = null;
            try {
                tmpSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(BT_UUID));
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.mSocket = tmpSocket;
        }

        @Override
        public void run() {
            try {
                mSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Socket's create() method failed", e);
                if (mHandler != null) {
                    Message errorMsg = mHandler.obtainMessage(MessageConstants.COMMUNICATION_ERROR);
                    errorMsg.sendToTarget();
                }
                cancel();
                return;
            }

            connectedThread = new BluetoothConnectedThread(mSocket);
            connectedThread.start();
            if (pendingPrint != null) {
                pendingPrint.run();
                pendingPrint = null;
            }
        }

        void cancel() {
            try {
                if (mSocket != null) {
                    mSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Could not close the client socket", e);
            }
        }

    }

    /**
     * Create Input Output streams using connected bluetooth socket thread
     */
    private class BluetoothConnectedThread extends Thread {

        private final BluetoothSocket mSocket;
        private final InputStream mInput;
        private final OutputStream mOutput;

        BluetoothConnectedThread(BluetoothSocket socket) {
            this.mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = mSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                tmpOut = mSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mInput = tmpIn;
            mOutput = tmpOut;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Log.d(TAG, pendingPrint != null ? "pending print" : "not pending");
                    BufferedReader br = new BufferedReader(new InputStreamReader(mInput));
                    String line = br.readLine();
                    String status = line != null ? line.replaceAll("\\p{Cc}", "") : "";
                    String code = status.length() > 1 ? status.substring(0, 2) : "";

                    Log.d(TAG, "Status Code: " + code);

                    if (mHandler == null) {
                        return;
                    }

                    Message msgIn;

                    switch (code) {
                        case "40":
                            msgIn = mHandler.obtainMessage(MessageConstants.PRINT_SUCCESS);
                            break;
                        case "15":
                            msgIn = mHandler.obtainMessage(MessageConstants.COVER_OPEN_ERROR);
                            break;
                        case "13":
                            msgIn = mHandler.obtainMessage(MessageConstants.LABEL_EMPTY);
                            break;
                        case "06":
                            msgIn = mHandler.obtainMessage(MessageConstants.COMMAND_SYNTAX_ERROR);
                            break;
                        case "11":
                            msgIn = mHandler.obtainMessage(MessageConstants.PAPER_JAM);
                            break;
                        default:
                            msgIn = mHandler.obtainMessage(MessageConstants.UNKNOWN_ERROR);
                            break;
                    }

                    if (msgIn != null) {
                        msgIn.sendToTarget();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Input stream was disconnected", e);
                    break;
                }
            }
        }

        void write(byte[] bytes) {
            try {
                mOutput.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "Unable to write output stream", e);
                if (mHandler != null) {
                    Message errorMsg = mHandler.obtainMessage(MessageConstants.COMMUNICATION_ERROR);
                    errorMsg.sendToTarget();
                }
            }
        }

    }

}
