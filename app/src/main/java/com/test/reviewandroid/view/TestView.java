package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @createTime: 2019-10-24
 * @author: lady_zhou
 * @Description: 练习自定义view
 */
public class TestView extends View {
    private Paint mPaint;//画笔
    private Path mArcPath;//路径

    private RectF mRectF;
    private ValueAnimator mAnimator;//动画
    private float value;//变化的值

    /*动效过程监听器*/
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initPaint();
        initPath();
        initListener();
        initAnimator();

        mAnimator.start();

    }

    /**
     * @return : void
     * @date 创建时间: 2019-10-24
     * @author lady_zhou
     * @Description 设置监听器
     */
    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
    }

    /**
     * @return : void
     * @date 创建时间: 2019-10-24
     * @author lady_zhou
     * @Description 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);//可以设置成属性元素
        mPaint.setStrokeWidth(15);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);//线条的两端是否带圆角


    }

    /**
     * @return : void
     * @date 创建时间: 2019-10-24
     * @author lady_zhou
     * @Description 初始化路径
     */
    private void initPath() {
        mRectF = new RectF();
        mArcPath = new Path();
        mRectF.set(50, 50, 350, 350);
    }

    /**
     * @return : void
     * @date 创建时间: 2019-10-24
     * @author lady_zhou
     * @Description 初始化动画
     */
    private void initAnimator() {
        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(3000);
        mAnimator.setStartDelay((long) (2000 * 0.23));

        mAnimator.addUpdateListener(mUpdateListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制弧形路径，画圆
        mArcPath.reset();
        mArcPath.addArc(mRectF, 0, 360 * value);
        canvas.drawPath(mArcPath, mPaint);
    }
}
