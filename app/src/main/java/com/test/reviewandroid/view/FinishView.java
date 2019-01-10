package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.test.reviewandroid.R;

/**
 * @createTime: 2019/1/8
 * @author: lady_zhou
 * @Description:
 */
public class FinishView extends View {
    // 画笔
    private Paint mPaint;

    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    // 当前的状态(非常重要)
    private FinishView.status mCurrentState = FinishView.status.NONE;

    /*路径*/
    private Path mTickPath;//对号路径
    private Path mCirclePath;//外援路径
    // 测量Path 并截取部分的工具
    private PathMeasure mPathMeasure;

    /*动画*/
    private ValueAnimator mCircleAnimator;//圆圈动画
    private ValueAnimator mTickAnimator;//对号动画

    /*颜色*/
    private int mTickColor;
    private int mBorderColor;
    private int mBackgroundColor;

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue = 0;

    /*动效过程监听器*/
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    // 用于控制动画状态转换
    private Handler mAnimatorHandler;

    public FinishView(Context context) {
        this(context, null);
    }

    public FinishView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FinishView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FinishView, 0, 0);
        try {
            mTickColor =
                    a.getColor(R.styleable.FinishView_tick_color, Color.WHITE);
            mBorderColor =
                    a.getColor(R.styleable.FinishView_border_color, Color.parseColor("#4AC65A"));
            mBackgroundColor = a.getColor(R.styleable.FinishView_background_color, Color.parseColor("#32bc43"));
        } finally {
            a.recycle();
        }
        initAll();
    }

    public static enum status {
        NONE,
        START,
        END
    }

    /**
     * @param widthMeasureSpec  :
     * @param heightMeasureSpec :
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 测量view大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    /**
     * @param measureSpec :  传入的高度或者宽度
     * @return : int
     * @date 创建时间: 2018/12/13
     * @author lady_zhou
     * @Description 测量宽或高
     */
    private int measureDimension(int measureSpec) {
        int defaultSize = 500;//默认大小
        int model = MeasureSpec.getMode(measureSpec);//获取模式
        int size = MeasureSpec.getSize(measureSpec);//获取大小

        switch (model) {
            case MeasureSpec.AT_MOST:
                return Math.min(size, defaultSize);//取最小的
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.UNSPECIFIED:
                return defaultSize;
            default:
                return defaultSize;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化所有
     */
    private void initAll() {
        initPaint();

        initPath();

        initListener();

        initHandler();

        initAnimator();

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化路径
     */
    private void initPath() {
        mTickPath = new Path();
        mCirclePath = new Path();

        mPathMeasure = new PathMeasure();

        RectF oval = new RectF(-100, -100, 100, 100);      // 外部圆环
        mCirclePath.addArc(oval, 45, -359.9f);

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 监听初始化
     */
    private void initListener() {
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化handle
     */
    private void initHandler() {
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化动画
     */
    private void initAnimator() {
    }
}
