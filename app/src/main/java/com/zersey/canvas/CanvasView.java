package com.zersey.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import android.content.Context;
import android.util.AttributeSet;


import androidx.annotation.Nullable;


public class CanvasView extends View {
  private Path path;
  private Paint brush,canvasPaint;
  private int paintColor;
  private Canvas canvas;
  private Bitmap bitmap;
  boolean erase = false;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private float curBrushSize;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawng();
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    private void setupDrawng() {
        path = new Path();
        brush = new Paint();
        brush.setAntiAlias(true);
        brush.setColor(Color.BLACK);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        float smallBrush = getResources().getInteger(R.integer.small_size);
        brush.setStrokeWidth(smallBrush);
        canvasPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.drawBitmap(bitmap,0,0,canvasPaint);
        canvas.drawPath(path,brush);
        canvas.restore();
    }
        @Override
    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
       float pointY = event.getY();
       mScaleDetector.onTouchEvent(event);
       switch (event.getAction()){
           case MotionEvent.ACTION_DOWN :
               path.moveTo(pointX,pointY);
               return true;
           case MotionEvent.ACTION_MOVE:
                       if(erase) {
                           path.lineTo(pointX, pointY);
                           canvas.drawPath(path, brush);
                           path.reset();
                           path.moveTo(pointX, pointY);
                        }
                       else {
                           path.lineTo(pointX, pointY);
                       }
               break;
           case MotionEvent.ACTION_UP:
               canvas.drawPath(path,brush);
               path.reset();
               break;
           default:
               return false;
       }
       //calls the onDraw method
       postInvalidate();
       return false;
    }
    public void setColor(String newColor){
        invalidate();
        float smallBrush = getResources().getInteger(R.integer.small_size);
        brush.setStrokeWidth(smallBrush);
        paintColor = Color.parseColor(newColor);
        brush.setColor(paintColor);
    }
    public void setBrushSize(float newSize){
       brush.setPathEffect(new CornerPathEffect(newSize));
       brush.setMaskFilter(null);
       brush.setStrokeWidth(newSize);
    }
    public void setErase(boolean isErase){
        this.erase = isErase;
        if(isErase) {
            brush.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            brush.setXfermode(null);
        }
    }
    public void setDotMode(){
        brush.setMaskFilter(null);
        brush.setPathEffect(new DashPathEffect(new float[] { 8, 8 }, 0));
    }

    public void setBlurMode(){
        brush.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 2.0f));
            invalidate();
            return true;
        }
    }
}

