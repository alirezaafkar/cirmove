package com.alirezaafkar.cirmove;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Alireza Afkar on 8/20/2017 AD.
 */

public class CirMove extends View {
    private static final int DEFAULT_DELAY = 0;
    private static final int DEFAULT_DURATION = 3000;
    private static final float DEFAULT_MAX_ALPHA = 1f;
    private static final float DEFAULT_MIN_ALPHA = 0f;
    private static final boolean DEFAULT_PREVIEW = false;
    private static final int DEFAULT_COLOR = Color.YELLOW;
    private static final boolean DEFAULT_AUTO_START = true;
    private static final int DEFAULT_REPEAT_MODE = ValueAnimator.REVERSE;
    private static final int DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE;
    private static final int DEFAULT_MAX_Y = 300;
    private static final int DEFAULT_MAX_X = 100;
    private static final int DEFAULT_MIN_Y = -300;
    private static final int DEFAULT_MIN_X = -100;

    private int mX = getRandomX();
    private int mY = getRandomY();
    private int mMinY = DEFAULT_MIN_Y;
    private int mMinX = DEFAULT_MIN_X;
    private int mMaxX = DEFAULT_MAX_X;
    private int mMaxY = DEFAULT_MAX_Y;
    private int mColor = DEFAULT_COLOR;
    private int mStartDelay = DEFAULT_DELAY;
    private int mDuration = DEFAULT_DURATION;
    private float mMaxAlpha = DEFAULT_MAX_ALPHA;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;
    private int mRepeatMode = DEFAULT_REPEAT_MODE;
    private boolean mAutoStart = DEFAULT_AUTO_START;
    private int mRepeatCount = DEFAULT_REPEAT_COUNT;

    private Paint mPaint;
    private AnimatorSet mAnimationSet;

    public CirMove(Context context) {
        super(context);
        init();
    }

    public CirMove(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    private void init() {
        initPaint();
        initAnimators();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray attrsArray = getContext()
                .obtainStyledAttributes(attrs, R.styleable.CirMove);
        mX = attrsArray.getInteger(R.styleable.CirMove_cirmove_x, getRandomX());
        mY = attrsArray.getInteger(R.styleable.CirMove_cirmove_y, getRandomY());
        mColor = attrsArray.getColor(R.styleable.CirMove_cirmove_color, DEFAULT_COLOR);
        mMinX = attrsArray.getInteger(R.styleable.CirMove_cirmove_min_x, DEFAULT_MIN_X);
        mMinY = attrsArray.getInteger(R.styleable.CirMove_cirmove_min_y, DEFAULT_MIN_Y);
        mMaxX = attrsArray.getInteger(R.styleable.CirMove_cirmove_max_x, DEFAULT_MAX_X);
        mMaxY = attrsArray.getInteger(R.styleable.CirMove_cirmove_max_y, DEFAULT_MAX_Y);
        mMinAlpha = attrsArray.getFloat(R.styleable.CirMove_cirmove_min_alpha, DEFAULT_MIN_ALPHA);
        mMaxAlpha = attrsArray.getFloat(R.styleable.CirMove_cirmove_max_alpha, DEFAULT_MAX_ALPHA);
        mDuration = attrsArray.getInteger(R.styleable.CirMove_cirmove_duration, DEFAULT_DURATION);
        mStartDelay = attrsArray.getInteger(R.styleable.CirMove_cirmove_start_delay, DEFAULT_DELAY);

        boolean preview = attrsArray
                .getBoolean(R.styleable.CirMove_cirmove_preview, DEFAULT_PREVIEW);
        mAutoStart = attrsArray
                .getBoolean(R.styleable.CirMove_cirmove_auto_start, DEFAULT_AUTO_START);
        mRepeatMode = attrsArray
                .getInteger(R.styleable.CirMove_cirmove_repeat_mode, DEFAULT_REPEAT_MODE);
        mRepeatCount = attrsArray
                .getInteger(R.styleable.CirMove_cirmove_repeat_count, DEFAULT_REPEAT_COUNT);

        if (isInEditMode() && preview) {
            setX(mX);
            setY(mY);
        }

        attrsArray.recycle();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(mColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void initAnimators() {
        int repeatCount = mRepeatCount == 0 ? ValueAnimator.INFINITE : mRepeatCount;
        int repeatMode = mRepeatMode == 1 ? ValueAnimator.RESTART : ValueAnimator.REVERSE;

        ObjectAnimator moveY = ObjectAnimator.ofFloat(this, "Y", mY);
        moveY.setRepeatMode(repeatMode);
        moveY.setRepeatCount(repeatCount);

        ObjectAnimator moveX = ObjectAnimator.ofFloat(this, "X", mX);
        moveX.setRepeatMode(repeatMode);
        moveX.setRepeatCount(repeatCount);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", mMaxAlpha, mMinAlpha);
        alpha.setRepeatMode(repeatMode);
        alpha.setRepeatCount(repeatCount);

        mAnimationSet = new AnimatorSet();
        mAnimationSet.playTogether(moveX, moveY, alpha);
        mAnimationSet.setStartDelay(mStartDelay);
        mAnimationSet.setDuration(mDuration);

        if (!isInEditMode() && mAutoStart) {
            start();
        }
    }

    private int getRandomX() {
        return getRandomNumber(mMinX, mMaxX);
    }

    private int getRandomY() {
        return getRandomNumber(mMinY, mMaxY);
    }

    private int getRandomNumber(int min, int max) {
        if (min > max) min = 0;
        return min + new Random().nextInt(max - min + 1);
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setStartDelay(int startDelay) {
        mStartDelay = startDelay;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public void setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
    }

    public void setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
    }

    public void setCirX(int x) {
        mX = x;
    }

    public void setCirY(int y) {
        mY = y;
    }

    public void setMinAlpha(float alpha) {
        mMinAlpha = alpha;
    }

    public void setMaxAlpha(float alpha) {
        mMaxAlpha = alpha;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        int padding = getMaxPadding();
        int radius = Math.max(width, height);
        canvas.drawCircle(width, height, radius - padding, mPaint);
    }


    private int getMaxPadding() {
        return Math.max(Math.max(getPaddingLeft(), getPaddingRight())
                , Math.max(getPaddingTop(), getPaddingBottom()));
    }

    public void start() {
        mAnimationSet.start();
    }

    public void stop() {
        mAnimationSet.cancel();
    }
}
