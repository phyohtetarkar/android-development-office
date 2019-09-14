package com.team.androidpos.util;

import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class BindingUtil {

    @BindingAdapter("android:text")
    public static void setNumber(TextView textView, long value) {
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setNumber(TextView textView, double value) {
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("android:text")
    public static void setNumber(EditText editText, double value) {
        if (value > 0) editText.setText(String.valueOf(value));
    }

    @BindingAdapter("path")
    public static void setImageUri(ImageView imageView, String imageFilePath) {
        if (imageFilePath != null && !imageFilePath.isEmpty()) {
            imageView.setImageURI(Uri.parse(imageFilePath));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static double getNumber(EditText editText) {
        String value = editText.getText().toString();
        return value.isEmpty() ? 0.0 : Double.parseDouble(value);
    }

}
