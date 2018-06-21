package com.wangsz.libs.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.wangsz.wusic.R;

/**
 * author: wangsz
 * date: On 2018/6/7 0007
 * 播放按钮
 */
public class PlayView extends FrameLayout implements View.OnClickListener {

    private long MAX = 100;
    private float mProgress = 0;
    private boolean mPlaying = false;
    private boolean mFinished = false;
    private ObjectAnimator mAnimator;
    private Paint mPaint = new Paint();
    private int mIntPaintWidth = 4;

    private Bitmap mBitmapPlay;
    private Bitmap mBitmapPause;

    private OnClickPlayListener onClickPlayListener;

    public PlayView(@NonNull Context context) {
        this(context, null);
    }

    public PlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mIntPaintWidth);
        oval = new RectF();
        mBitmapPlay = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.play);
        mBitmapPause = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.pause);
        setOnClickListener(this);
    }

    public void initAnim(long duration) {
        MAX = duration;
        mAnimator = ObjectAnimator.ofFloat(this, "progress", 0, MAX);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mProgress = 0;
                mPlaying = false;
                mFinished = true;
                postInvalidate();
            }
        });
        mAnimator.start();
        mPlaying = true;
        postInvalidate();
    }

    public void setOnClickPlayListener(OnClickPlayListener onClickPlayListener) {
        this.onClickPlayListener = onClickPlayListener;
    }

    private int mRadius;
    private RectF oval;

    private int x = 0;
    private int y = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadius == 0) {
            mRadius = Math.min(getHeight(), getWidth()) / 2 - mIntPaintWidth / 2;
            x = getWidth() / 2;
            y = getHeight() / 2;
        }
        // 画背景圆
        if (mPlaying) {
            mPaint.setColor(0xffcccccc);
        } else {
            mPaint.setColor(0xff999999);
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);

        oval.set(mIntPaintWidth / 2, mIntPaintWidth / 2, getWidth() - mIntPaintWidth / 2, getHeight() - mIntPaintWidth / 2);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.drawArc(oval, 270, 360 * mProgress / MAX, false, mPaint);
        if (mPlaying) {
            canvas.drawBitmap(mBitmapPause, x - mBitmapPause.getHeight() / 2, y - mBitmapPause.getWidth() / 2, mPaint);
        } else {
            canvas.drawBitmap(mBitmapPlay, x - mBitmapPlay.getHeight() / 2, y - mBitmapPlay.getWidth() / 2, mPaint);
        }
    }

    @Override
    public void onClick(View v) {
        if (mPlaying) {
            mAnimator.pause();
            mPlaying = false;
            postInvalidate();
            if (onClickPlayListener != null) {
                onClickPlayListener.click();
            }
        } else {
            if (mAnimator.isPaused()) {
                mAnimator.resume();
                if (onClickPlayListener != null) {
                    onClickPlayListener.click();
                }
            } else {
                if (mFinished && onClickPlayListener != null) {
                    onClickPlayListener.click();
                    mFinished = false;
                }
                mAnimator.start();
            }
            mPlaying = true;
            postInvalidate();
        }
    }

    private void setProgress(float progress) {
        if (!mPlaying || mProgress == progress) return;
//        Log.d("setProgress", progress + "");
        this.mProgress = progress;
        postInvalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("onDetachedFromWindow", "onDetachedFromWindow");
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        mBitmapPause.recycle();
        mBitmapPlay.recycle();
    }

    public interface OnClickPlayListener {
        void click();
    }

}
