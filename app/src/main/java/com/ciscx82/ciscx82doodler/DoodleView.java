package com.ciscx82.ciscx82doodler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DoodleView extends View {
    // params
    private Paint paint;
    Paint bPaint = new Paint(Paint.DITHER_FLAG);

    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;
    private ArrayList<Stroke> strokes = new ArrayList<>();

    private int color;
    private float brushSize = 20;
    private int opacity = 255;

    public DoodleView(Context context) {
        super(context);
    }

    public DoodleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        path = new Path();

        // set stroke settings
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        // default: color = black, brush size = 20, opacity = 255
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(brushSize);
        paint.setAlpha(opacity);
    }

    // function for clearing the screen
    public void clearCanvas() {
        canvas.drawColor(Color.WHITE);
        invalidate();
    }

    // functions for changing brush settings
    public void setBrushColor(int newColor) {
        color = newColor;
        paint.setColor(color);
    }
    public void setBrushSize(float newSize) {
        brushSize = newSize;
        paint.setStrokeWidth(brushSize);
    }
    public void setOpacity(int newOpacity) {
        opacity = newOpacity;
        paint.setAlpha(opacity);
    }

    // create bitmap and canvas with correct size
    @Override
    protected void onSizeChanged(int width, int height, int fWidth, int fHeight) {
        super.onSizeChanged(width, height, fWidth, fHeight);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE); // background color
    }

    // main drawing method
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, bPaint);

        // iterate over strokes, draw each onto canvas
        for (Stroke s: strokes) {
            paint.setColor(s.color);
            paint.setStrokeWidth(s.strokeWidth);
            paint.setAlpha(s.opacity);
            canvas.drawPath(s.path, paint);
        }
    }

    private void touchStart(float x, float y) {
        path = new Path();
        Stroke newStroke = new Stroke(color, brushSize, opacity, path);
        strokes.add(newStroke);

        // finally remove any curve
        // or line from the path
        path.reset();

        // this methods sets the starting
        // point of the line being drawn
        path.moveTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }
        invalidate();
        return true;
    }
}
