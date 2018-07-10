package com.cdxxgc.expressagesearchdemo.widget;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.utils.TransFormUtil;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * Created by Administrator on 2018/6/15.
 * function：
 * 圆形倒转计时器
 */

public class CustomCountDown extends View {

    private static final String TAG = "CustomCountDown";

    private int mDefaultCircleWidth = 5;
    private int mDefaultWidth = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
    private int mDefaultHeight = (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
    ;
    private int mCircleColor;
    private int mCircleWidth;
    private int mPdl;
    private int mPdr;
    private int mPdt;
    private int mPdb;
    private RectF mRec;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float progress = 0;

    private CountDownHandler mCountDownHandler;
    private CountDownThread workThread = new CountDownThread();
    private CountDownTimeThread workTimeThread = new CountDownTimeThread();
    private float mDefaultSecond = 3f;
    private float mSecond;

    public void setmCountDownHandler(CountDownHandler mCountDownHandler) {
        this.mCountDownHandler = mCountDownHandler;
    }

    public CustomCountDown(Context context) {
        this(context, null);
    }

    public CustomCountDown(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomCountDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomCountDown);
        int length = ta.length();
        for (int i = 0; i < length; i++) {

            int index = ta.getIndex(i);

            switch (index) {
                case R.styleable.CustomCountDown_circleColor:
                    mCircleColor = ta.getColor(index, Color.RED);
                    break;

                case R.styleable.CustomCountDown_circleWidth:
                    mCircleWidth = ta.getDimensionPixelSize(index, (int) TypedValue.applyDimension(COMPLEX_UNIT_DIP, mDefaultCircleWidth, context.getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomCountDown_second:
                    mSecond = ta.getFloat(index, mDefaultSecond);
                    break;
            }
        }

        ta.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(TypedValue.applyDimension(COMPLEX_UNIT_PX, mCircleWidth * 4, getResources().getDisplayMetrics()));

        workThread.start();
        workTimeThread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w_mode = MeasureSpec.getMode(widthMeasureSpec);
        int h_mode = MeasureSpec.getMode(heightMeasureSpec);
        int w_size = MeasureSpec.getSize(widthMeasureSpec);
        int h_size = MeasureSpec.getSize(heightMeasureSpec);

        if (w_mode == MeasureSpec.AT_MOST && h_mode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, mDefaultHeight);
        } else if (w_mode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mDefaultWidth, h_size);
        } else if (h_mode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(w_size, mDefaultHeight);
        }

        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPdl = getPaddingLeft();
        mPdr = getPaddingRight();
        mPdt = getPaddingTop();
        mPdb = getPaddingBottom();

        mRec = new RectF(0 + mPdl, 0 + mPdt, mWidth - mPdr, mHeight - mPdb);

        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setColor(mCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(mRec, -90, progress, false, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText((int) mSecond + "s",
                getWidth() / 2 - TransFormUtil.dp2px(getContext(), 6),
                getHeight() / 2 + TransFormUtil.dp2px(getContext(), 5), mPaint);
    }

    public interface CountDownHandler {
        void end();
    }

    class CountDownThread extends Thread {

        @Override
        public void run() {
            float speed = (mSecond / 360) * 1000;
            while (progress < 360) {
                progress++;

                try {
                    Thread.sleep((long) speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }

            if (mCountDownHandler != null) {
                mCountDownHandler.end();
            }
        }
    }

    class CountDownTimeThread extends Thread {

        @Override
        public void run() {
            while (mSecond > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                postInvalidate();
                --mSecond;
            }
        }
    }

    public void onStop(){
        if (mCountDownHandler != null) {
            mCountDownHandler = null;
        }
        if (workThread != null) {
            workThread.interrupt();
            workThread = null;
        }

        if (workTimeThread != null) {
            workTimeThread.interrupt();
            workTimeThread = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "=========onDetachedFromWindow===========");
        onStop();
    }
}
