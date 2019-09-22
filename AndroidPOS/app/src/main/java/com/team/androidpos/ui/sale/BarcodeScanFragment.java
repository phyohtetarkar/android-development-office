package com.team.androidpos.ui.sale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.team.androidpos.R;

import java.util.Arrays;
import java.util.List;

public class BarcodeScanFragment extends Fragment {

    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastCode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beepManager = new BeepManager(requireActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barcodeView = view.findViewById(R.id.barcodeView);
        barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(Arrays.asList(BarcodeFormat.values())));
        barcodeView.setStatusText("");
        barcodeView.setTorchOn();
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() == null || result.getText().equals(lastCode)) {
                    return;
                }

                lastCode = result.getText();
                // TODO
                Log.d("TAG", "code: " + result.getText());
                beepManager.playBeepSound();
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });

        AppCompatToggleButton btnTorch = view.findViewById(R.id.btnTorch);
        btnTorch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                barcodeView.setTorchOn();
            } else {
                barcodeView.setTorchOff();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        barcodeView = null;
    }
}
