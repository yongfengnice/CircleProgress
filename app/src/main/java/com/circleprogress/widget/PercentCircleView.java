package com.circleprogress.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.circleprogress.R;

public class PercentCircleView extends View {

    private Paint circlePaint = new Paint();
    private int textGrayColor = ContextCompat.getColor(getContext(), R.color.color_gray);
    private int textBlueColor = ContextCompat.getColor(getContext(), R.color.color_blue);
    private float viewRadius = 0f;
    private float circleSize = dip2px(10f);
    private float minRadius = 0f;
    private float maxRadius = 0f;
    private float strokeWidth = dip2px(0.2f);
    private int percent = 0;
    private RectF rectF = null;
    private Rect textRect = new Rect();

    public PercentCircleView(Context context) {
        this(context, null);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(textBlueColor);
        circlePaint.setTextSize(sp2px(20));
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setFakeBoldText(false);
        circlePaint.getTextBounds("8", 0, 1, textRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewRadius = Math.min(getMeasuredWidth(), getMeasuredHeight()) * 1.0f / 2;
        maxRadius = viewRadius - strokeWidth;
        minRadius = viewRadius - circleSize;
        rectF = new RectF(circleSize / 2, circleSize / 2, 2 * viewRadius - circleSize / 2 - strokeWidth, 2 * viewRadius - circleSize / 2 - strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(circleSize);
        circlePaint.setColor(textGrayColor);
        canvas.drawCircle(viewRadius + strokeWidth / 2, viewRadius + strokeWidth / 2, maxRadius - circleSize / 2, circlePaint);

        circlePaint.setColor(textBlueColor);
        circlePaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(viewRadius + strokeWidth / 2, viewRadius + strokeWidth / 2, maxRadius, circlePaint);
        canvas.drawCircle(viewRadius + strokeWidth / 2, viewRadius + strokeWidth / 2, minRadius, circlePaint);

        circlePaint.setStrokeWidth(circleSize - strokeWidth);
        canvas.drawArc(rectF, -92f, 360 * (percent * 1.0f / 100), false, circlePaint);

        circlePaint.setStrokeWidth(0);
        canvas.drawText(percent + "%", viewRadius, viewRadius + (textRect.bottom - textRect.top) / 2, circlePaint);
    }

    private float dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return dipValue * scale;
    }

    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spVal, getContext().getResources().getDisplayMetrics()
        );
    }

    public void setProgress(int progress) {
        percent = progress;
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;
        invalidate();
    }

}
