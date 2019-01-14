package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.test.reviewandroid.R;

/**
 * @createTime: 2019/1/8
 * @author: lady_zhou
 * @Description: 完成时的动画
 */
public class FinishView extends View {
    private static final String TAG = "FinishView";
    // View 宽高
    private int mViewWidth;
    private int mViewHeight;

    private int mRadius;//半径

    private Point mCenterPoint;//中心点
    private Point mLeftPoint, mMiddlePoint, mRightPoint, mStopPoint;

    private int mTickWidth, mBorderWidth;//设置对话和圆圈的宽度

    // 当前的状态(非常重要)
    private FinishView.status mCurrentState = FinishView.status.NONE;

    /*路径*/
    private Path mTicktPath;//对号路径
    private Path mCirclePath;//外圆路径
    private RectF mRectF;
    private Path mArcPath;


    // 测量Path 并截取部分的工具
    private PathMeasure mPathMeasure;

    /*画笔*/
    private Paint mCirclePaint;//外圆画笔
    private Paint mTickPaint;//对号画笔

    /*动画*/
    private ValueAnimator mCircleAnimator;//圆圈动画
    private ValueAnimator mTickAnimator;//对号动画

    /*颜色*/
    private int mTickColor;
    private int mCircleColor;
    private int mBackgroundColor;

    // 动画数值(用于控制动画状态,因为同一时间内只允许有一种状态出现,具体数值处理取决于当前状态)
    private float mAnimatorValue = 0;

    /*动效过程监听器*/
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    // 用于控制动画状态转换
    private Handler mAnimatorHandler;

    private final static int UNSET_FLAG = 1;
    private float value;


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
            mCircleColor =
                    a.getColor(R.styleable.FinishView_circle_view_color, Color.WHITE);
            mBackgroundColor = a.getColor(R.styleable.FinishView_background_color, Color.parseColor("#32bc43"));
            mTickWidth = a.getDimensionPixelSize(R.styleable.FinishView_tick_width, UNSET_FLAG);
            mBorderWidth =
                    a.getDimensionPixelOffset(R.styleable.FinishView_border_width, UNSET_FLAG);
        } finally {
            a.recycle();
        }
        setBackgroundColor(mBackgroundColor);
        initAll();
    }

    public static enum status {
        NONE,
        STARTLIFT,
        STARTCIRCLE
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

        int width = getWidth();
        int height = getHeight();

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        mViewWidth = width - paddingLeft - paddingRight;
        mViewHeight = height - paddingTop - paddingBottom;

        int diameter = Math.min(mViewWidth, mViewHeight);
        mRadius = Math.min(mViewWidth, mViewHeight) / 2 - mBorderWidth;

        mCenterPoint.x = paddingLeft + mRadius + mBorderWidth;
        mCenterPoint.y = paddingTop + mRadius + mBorderWidth;

        mRectF.set(paddingLeft + mBorderWidth, paddingTop + mBorderWidth, mViewWidth - mBorderWidth, mViewHeight - mBorderWidth);


        if (mBorderWidth == UNSET_FLAG) {
            mBorderWidth = mViewWidth / 12;
        }
        if (mTickWidth == UNSET_FLAG) {
            mTickWidth = mViewWidth / 10;
        }

        mLeftPoint.x = (int) (paddingLeft + (diameter * 0.2428));
        mLeftPoint.y = (int) (paddingTop + diameter * 0.4712);

        mMiddlePoint.x = (int) (paddingLeft + (diameter * 0.4571));
        mMiddlePoint.y = (int) (paddingTop + diameter * 0.6642);

        mStopPoint.x = (int) (paddingLeft + diameter * 0.4581);
        mStopPoint.y = (int) (paddingTop + diameter * 0.6652);

        mRightPoint.x = (int) (paddingLeft + (diameter * 0.7642));
        mRightPoint.y = (int) (paddingTop + diameter * 0.3285);

        mTicktPath.moveTo(mLeftPoint.x, mLeftPoint.y);
        mTicktPath.lineTo(mMiddlePoint.x, mMiddlePoint.y);
        mTicktPath.lineTo(mStopPoint.x, mStopPoint.y);
        mTicktPath.lineTo(mRightPoint.x, mRightPoint.y);
        mPathMeasure.setPath(mTicktPath, false);
        mTicktPath.reset();


        mCirclePath.reset();
        mCirclePath.addCircle(mCenterPoint.x, mCenterPoint.y, mRadius, Path.Direction.CCW);
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

        mCurrentState = status.STARTCIRCLE;
        mCircleAnimator.start();

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化画笔
     */
    private void initPaint() {
        //外圆画笔初始化
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(mBorderWidth);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        //对号画笔初始化
        mTickPaint = new Paint();
        mTickPaint.setStyle(Paint.Style.STROKE);
        mTickPaint.setStrokeWidth(mTickWidth);
        mTickPaint.setColor(mTickColor);
        mTickPaint.setAntiAlias(true);
        mTickPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化路径
     */
    private void initPath() {
        mCenterPoint = new Point();
        mCirclePath = new Path();
        mRectF = new RectF();
        mArcPath = new Path();

        mLeftPoint = new Point();
        mMiddlePoint = new Point();
        mRightPoint = new Point();
        mStopPoint = new Point();

        mPathMeasure = new PathMeasure();
        mTicktPath = new Path();

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 监听初始化
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
                // getHandle发消息通知动画状态更新
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
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化handle
     */
    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case STARTCIRCLE:
                        mCurrentState = status.STARTLIFT;
                        mCircleAnimator.removeAllListeners();//删除动画对象所有的监听器（包括暂停监听器）
                        mTickAnimator.start();
                        break;
                    case STARTLIFT:
                        mTickAnimator.removeAllListeners();
                        break;
                }
            }
        };
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/8
     * @author lady_zhou
     * @Description 初始化动画
     */
    private void initAnimator() {
        // circle animation
        mCircleAnimator = ValueAnimator.ofFloat(0f, 1f);
        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setDuration(2000 / 3);
        mCircleAnimator.setStartDelay((long) (2000 * 0.23));


        mTickAnimator = ValueAnimator.ofFloat(0f, 1f);
        mTickAnimator.setStartDelay((long) (2000 * 0.21));
        mTickAnimator.setDuration(2000 / 7);
        mTickAnimator.setInterpolator(new LinearInterpolator());


        mCircleAnimator.addUpdateListener(mUpdateListener);
        mTickAnimator.addUpdateListener(mUpdateListener);

        mCircleAnimator.addListener(mAnimatorListener);
        mTickAnimator.addListener(mAnimatorListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTick(canvas);
    }

    private void drawTick(Canvas canvas) {
        canvas.drawPath(mArcPath, mCirclePaint);

        switch (mCurrentState) {

            case STARTLIFT:
                canvas.drawPath(mTicktPath, mTickPaint);
                break;

            case STARTCIRCLE:
                mArcPath.reset();
                mArcPath.addArc(mRectF, -159, 360 * value);
                canvas.drawPath(mArcPath, mCirclePaint);
                break;
        }
    }
}
