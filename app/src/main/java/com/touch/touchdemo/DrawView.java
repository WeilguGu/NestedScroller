package com.touch.touchdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weilgu
 * @time 2020/6/23
 * @des
 */
public class DrawView extends View {

    private float mStartX;
    private float mStartY;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private GestureDetector.OnGestureListener mGestureListener;// 触摸手势监听

    private Paint mPaint = new Paint();
    private List<Path> mPathList = new ArrayList<>(); // 保存涂鸦轨迹的集合
    private float mLastX, mLastY;
    private Path mCurrentPath; // 当前的涂鸦轨迹

    public DrawView(Context context) {
        this(context,null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 设置画笔
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mGestureListener = new GestureDetector.OnGestureListener() {
            //用户按下屏幕就会触发；
            @Override
            public boolean onDown(MotionEvent e) {
                mStartX = e.getX();
                mStartY = e.getY();
                mCurrentPath = new Path();
                mPathList.add(mCurrentPath);
                Log.i("weilgu","mStartX = " + mStartX + "  mStartY = " + mStartY);
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("weilgu","e1.getX = " + e1.getX() + "   e1.getY = " + e1.getY());
                Log.i("weilgu","e2.getX = " + e2.getX() + "   e2.getY = " + e2.getY());
                return false;
            }

            //长按触摸屏，超过一定时长，就会触发这个事件
            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null){
            canvas.drawBitmap(mBitmap,0,0,null);
        }
    }

    private void initBitmap(){
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        /*witch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                if (mBitmap == null){
                    initBitmap();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }*/
        return true;
    }
}
