package com.circleprogress.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

/**
 * Creator: syf(205205)
 * Date   : on 2016/9/2 0002
 * Desc   : 自定义圆角进度条
 */
public class CircleProgressBar extends View {
    private Paint mBackgroundPaint;//背景画笔
    private Paint mProgressPaint;//进度画笔

    private int mViewWidth;
    private int mViewHeight;
    private int mMaxProgress = 100;//最大进度
    private int mProgress = 0;//当前进度

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(0xff999999);//默认背景颜色
        setBackgroundResource(0);//移除设置的背景资源

        mProgressPaint = new Paint();//进度条画笔
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(0xff38b0a2);//默认背景颜色
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        RectF bgRectF = new RectF(0, 0, mViewWidth, mViewHeight);
        canvas.drawRoundRect(bgRectF, mViewHeight / 2, mViewHeight / 2, mBackgroundPaint);
        //画进度条
        int width = (int) (mViewWidth * (mProgress * 1.0f / mMaxProgress) + 0.5);
        if (width <= mViewHeight) {//圆形
            canvas.drawCircle(width / 2, mViewHeight / 2, width / 2, mProgressPaint);
        } else {
            RectF progressRectF = new RectF(0, 0, width, mViewHeight);
            canvas.drawRoundRect(progressRectF, mViewHeight / 2, mViewHeight / 2, mProgressPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        invalidate();
    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        progress = progress >= 0 ? progress : 0;
        progress = progress <= mMaxProgress ? progress : mMaxProgress;
        mProgress = progress;
        invalidate();
    }

    /**
     * 设置最大进度
     *
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    /** 设置背景颜色 */
    public void setBgColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
    }

    /** 设置进度条颜色 */
    public void setProgressColor(@ColorInt int color) {
        mProgressPaint.setColor(color);
    }
}
