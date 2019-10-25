package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
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

    private Paint mTickPaint;//对号画笔
    private ValueAnimator mTickAnimator;//对号动画
    private Path mTicktPath;//对号路径
    // 测量Path 并截取部分的工具
    private PathMeasure mPathMeasure;

    /*动效过程监听器*/
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    // 当前的状态(非常重要)
    private status mCurrentState = status.NONE;

    // 用于控制动画状态转换
    private Handler mAnimatorHandler;


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

        initHandler();
        initAnimator();

        mCurrentState = status.STARTCIRCLE;
        mAnimator.start();

    }

    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case STARTCIRCLE:
                        mCurrentState = status.STARTLIFT;
                        mAnimator.removeAllListeners();//删除动画对象所有的监听器（包括暂停监听器）
                        mTickAnimator.start();
                        break;
                    case STARTLIFT:
                        mTickAnimator.removeAllListeners();
                        break;
                }
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();

        //框住要画的圆
        mRectF.set((width / 2) - 70, (height / 2) - 70, (width / 2) + 70, (height / 2) + 70);

        mTicktPath.moveTo((width / 2) - 20, (height / 2) - 25);
        mTicktPath.lineTo(width / 2, (height / 2) + 15);
        mTicktPath.lineTo(width / 2 + 5, (height / 2) + 15);
        mTicktPath.lineTo((width / 2) + 35, (height / 2) - 30);

        mPathMeasure.setPath(mTicktPath, false);
        mTicktPath.reset();

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
                if (mCurrentState == status.STARTLIFT) {
                    mTicktPath.reset();
                    mPathMeasure.getSegment(0, value * mPathMeasure.getLength(), mTicktPath, true);
                }
                invalidate();
            }
        };

        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画完成
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

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

        mTickPaint = new Paint();
        mTickPaint.setColor(Color.BLACK);//可以设置成属性元素
        mTickPaint.setStrokeWidth(15);
        mTickPaint.setAntiAlias(true);
        mTickPaint.setStyle(Paint.Style.STROKE);
        mTickPaint.setStrokeCap(Paint.Cap.ROUND);//线条的两端是否带圆角

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

        mTicktPath = new Path();
        mPathMeasure = new PathMeasure();

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

        mTickAnimator = ValueAnimator.ofFloat(0f, 1f);
        mTickAnimator.setInterpolator(new LinearInterpolator());
        mTickAnimator.setDuration(3000);
        mTickAnimator.setStartDelay((long) (2000 * 0.23));

        mAnimator.addUpdateListener(mUpdateListener);
        mTickAnimator.addUpdateListener(mUpdateListener);

        mAnimator.addListener(mAnimatorListener);
        mTickAnimator.addListener(mAnimatorListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mArcPath, mPaint);

        switch (mCurrentState) {

            case STARTLIFT:
                canvas.drawPath(mTicktPath, mTickPaint);
                break;

            case STARTCIRCLE:
                //绘制弧形路径，画圆
                mArcPath.reset();
                mArcPath.addArc(mRectF, 0, 360 * value);
                canvas.drawPath(mArcPath, mPaint);

                break;
        }
    }

    public static enum status {
        NONE,
        STARTLIFT,
        STARTCIRCLE
    }
}
