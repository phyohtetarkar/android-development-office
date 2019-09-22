package com.team.androidpos.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class PermissionUtil {

    public static final int PERMISSION_CAMERA = 100;

    public static boolean hasCameraPermission(Fragment fragment) {
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            fragment.requestPermissions(new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA);
            return false;
        }
        return true;
    }

}
