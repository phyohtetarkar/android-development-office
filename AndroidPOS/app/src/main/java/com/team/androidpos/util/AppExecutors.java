package com.team.androidpos.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class AppExecutors {

    private static Executor ioExecutor;
    private static Executor mainThreadExecutor;

    public static Executor io() {
        if (ioExecutor == null) {
            ioExecutor = Executors.newFixedThreadPool(3);
        }
        return ioExecutor;
    }

    public static Executor main() {
        if (mainThreadExecutor == null) {
            mainThreadExecutor = new MainThreadExecutor();
        }
        return mainThreadExecutor;
    }

    static class MainThreadExecutor implements Executor {

        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mHandler.post(command);
        }

    }

}
