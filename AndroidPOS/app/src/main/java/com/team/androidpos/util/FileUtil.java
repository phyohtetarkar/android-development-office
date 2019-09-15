package com.team.androidpos.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {

    public static Bitmap writeImage(Context context, Uri uri, File imageFile) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);

        final int REQUIRED_SIZE = 240;
        int width_tmp = options.outWidth;
        int height_tmp = options.outHeight;

        int scale = 1;
        while (width_tmp / 2 >= REQUIRED_SIZE && height_tmp / 2 >= REQUIRED_SIZE) {
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options scaledOpts = new BitmapFactory.Options();
        scaledOpts.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, scaledOpts);

        FileOutputStream fos = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

        return bitmap;
    }

}
