package com.team.androidpos.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import static android.graphics.Bitmap.Config.ALPHA_8;
import static android.graphics.Color.TRANSPARENT;

public class ConcaveView extends View {

    private Paint paint;
    private Paint mShadowPaint;
    private Paint strokePaint;
    private Path path;
    private Bitmap mShadow;
    private int currentHeight;

    public ConcaveView(Context context) {
        super(context);
        setup();
    }

    public ConcaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public ConcaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public ConcaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        path.reset();

        int height = getHeight() - 5;
        int width = getWidth() - 5;

        RectF rectLeftTop = new RectF(-45, -45, 55, 55);
        RectF rectRightTop = new RectF(width - 50, -45, width + 50, 55);
        RectF rectLeftBottom = new RectF(-45, height - 50, 50, height + 50);
        RectF rectRightBottom = new RectF(width - 50, height - 50, width + 50, height + 50);
        //canvas.drawArc(rectF, 0, 90, true, paint);
        path.arcTo(rectLeftTop, 0, 90, false);
        path.lineTo(5, height - 50);
        path.arcTo(rectLeftBottom, 270, 90, false);
        path.lineTo(width - 50, height);
        path.arcTo(rectRightBottom, 180, 90, false);
        path.lineTo(width, 50);
        path.arcTo(rectRightTop, 90, 90, false);
        path.moveTo(width - 50, 5);
        path.lineTo(55, 5);
        path.close();

        generateShadow();
        if (!isInEditMode()) {
            canvas.drawBitmap(mShadow, 0f, 10/2f, null);
        }

        canvas.drawPath(path, paint);
        //canvas.drawPath(path, strokePaint);
    }

    private void setup() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setAntiAlias(true);
        paint.setDither(true);

        path = new Path();

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(1);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setAntiAlias(true);

        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColorFilter(new PorterDuffColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN));
        mShadowPaint.setAlpha(51);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    private void generateShadow() {
        if (isInEditMode()) return;

        if (mShadow == null || currentHeight != getHeight()) {
            currentHeight = getHeight();
            mShadow = Bitmap.createBitmap(getWidth(), currentHeight, ALPHA_8);
        } else {
            mShadow.eraseColor(TRANSPARENT);
        }
        Canvas c = new Canvas(mShadow);
        c.drawPath(path, mShadowPaint);

        RenderScript rs = RenderScript.create(getContext());
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8(rs));
        Allocation input = Allocation.createFromBitmap(rs, mShadow);
        Allocation output = Allocation.createTyped(rs, input.getType());
        blur.setRadius(10);
        blur.setInput(input);
        blur.forEach(output);
        output.copyTo(mShadow);
        input.destroy();
        output.destroy();
        blur.destroy();
    }
}
