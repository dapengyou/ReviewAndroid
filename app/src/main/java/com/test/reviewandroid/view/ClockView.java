package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.test.reviewandroid.R;

/**
 * @createTime: 2018/12/13
 * @author: lady_zhou
 * @Description:
 */
public class ClockView extends View {
    private static final String TAG = "ClockView";
    private Canvas mCanvas;//全局画布

    private float mCanvasTranslateX;//指针的在x轴的位移
    private float mCanvasTranslateY;//指针的在y轴的位移
    private float mMaxCanvasTranslate;//指针的最大位移

    /*  渐变  */
    private SweepGradient mSweepGradient;//梯度扫描渐变
    private Matrix mGradientMatrix;//渐变矩阵，作用在SweepGradient

    /* 画笔 */
    private Paint mScaleArcPaint;//刻度圆弧画笔
    private Paint mScaleLinePaint;//刻度线画笔
    private Paint mTextPaint;//文字画笔
    private RectF mScaleArcRectF = new RectF();//刻度圆弧的外接矩形
    private Paint mSecondHandPaint;//秒针画笔
    private Paint mMilliSecondPaint;//毫秒针画笔
    private Paint mMillisecondCirclePaint;//毫秒针圆圈画笔

    /* 渐变颜色 */
    private int mLightColor;//亮色，用于分针、秒针、渐变终止色
    private int mDarkColor;//暗色，圆弧、刻度线、时针、渐变起始色
    private int mBackgroundColor;//背景色
    private float mTextSize;//计时文本字体大小

    private float mRadius;//时钟半径
    private float mMillisecondRadius;//毫秒圆圈半径
    private float mScaleLength;//刻度线长度

    /* 角度 */
    private float mSecondDegree;//秒针转过的角度
    private float mMilliSecondDegree; //毫秒针转过的角度
    private float mRound = 360f;//一圈的总度数

    /* 加一个默认的padding值，为了防止用camera旋转时钟时造成四周超出view大小 */
    private float mDefaultPadding;
    private float mPaddingLeft;
    private float mPaddingTop;
    private float mPaddingRight;
    private float mPaddingBottom;

    /* 秒针路径 */
    private Path mSecondHandPath = new Path();

    private ValueAnimator animator; //执行的属性动画
    private ValueAnimator backAnimator; //执行的属性动画

    private boolean isTrue;

    private int count;//动画执行次数,
    private float milliSecondCount = 0;//毫秒针走的次数
    private float secondCount = 0;//秒针走的次数
    private float minuteCount = 0;//分针走的次数
    private String time;//记录的时间
    private float mBackDegree;//回转的角度
    private float mMilliSecondBackDegree;//回转的角度

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClockView, 0, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.ClockView_clock_backgroundColor, Color.parseColor("#000000"));
        mLightColor = typedArray.getColor(R.styleable.ClockView_clock_lightColor, Color.parseColor("#ffffff"));
        mDarkColor = typedArray.getColor(R.styleable.ClockView_clock_darkColor, Color.parseColor("#40ffffff"));
        mTextSize = typedArray.getDimension(R.styleable.ClockView_clock_textSize, sp2px(context, 36));
//        将TypedArray对象回收
        typedArray.recycle();

        //初始化各类画笔
        initPaint();

        //初始化Matrix
        mGradientMatrix = new Matrix();

        //初始化数据
        mSecondDegree = 0;
        mMilliSecondDegree = 0;
        count = 0;

        //初始化动画
        initAnimator();

    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/21
     * @author lady_zhou
     * @Description 初始化各类画笔
     */
    private void initPaint() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mLightColor);
        //居中绘制文字
        mTextPaint.setTextAlign(Paint.Align.CENTER);
//        mTextPaint.setTextSize(mTextSize);

        //刻度线
        mScaleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleLinePaint.setStyle(Paint.Style.STROKE);
        mScaleLinePaint.setColor(mBackgroundColor);

        //表盘
        mScaleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleArcPaint.setStyle(Paint.Style.STROKE);
        mScaleArcPaint.setColor(mDarkColor);

        //秒针
        mSecondHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondHandPaint.setStyle(Paint.Style.FILL);
        mSecondHandPaint.setColor(mLightColor);

        //毫秒针
        mMilliSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMilliSecondPaint.setStyle(Paint.Style.FILL);
        mMilliSecondPaint.setColor(mLightColor);

        //毫秒针圆圈
        mMillisecondCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMillisecondCirclePaint.setStyle(Paint.Style.STROKE);
        mMillisecondCirclePaint.setColor(mLightColor);
    }

    /**
     * @param context :
     * @param spVal   :
     * @return : int
     * @date 创建时间: 2018/12/13
     * @author lady_zhou
     * @Description sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * @param widthMeasureSpec  :
     * @param heightMeasureSpec :
     * @return : void
     * @date 创建时间: 2018/12/13
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
        int defaultSize = 1000;//默认大小
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

    /**
     * @param w    : 宽度
     * @param h    : 高度
     * @param oldw :  上一次宽度
     * @param oldh :  上一次高度
     * @return : void
     * @date 创建时间: 2018/12/13
     * @author lady_zhou
     * @Description 确定view 大小，在视图大小发生改变时调用
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w - getPaddingLeft() - getPaddingRight(),
                h - getPaddingTop() - getPaddingBottom()) / 2;

        mDefaultPadding = 0.12f * mRadius;
        mMillisecondRadius = 0.25f * mRadius;//毫秒针半径

        //计算padding数值
        mPaddingLeft = mDefaultPadding + w / 2 - mRadius + getPaddingLeft();
        mPaddingRight = mDefaultPadding + w / 2 - mRadius + getPaddingRight();
        mPaddingTop = mDefaultPadding + h / 2 - mRadius + getPaddingTop();
        mPaddingBottom = mDefaultPadding + h / 2 - mRadius + getPaddingBottom();

        mScaleLength = 0.12f * mRadius;//根据比例确定刻度线长度

        //设置画笔宽度
        mScaleLinePaint.setStrokeWidth(0.1f * mScaleLength);
        mScaleArcPaint.setStrokeWidth(mScaleLength);
        mMilliSecondPaint.setStrokeWidth(0.01f * mScaleLength);

        //梯度扫描渐变，以(w/2,h/2)为中心点，两种起止颜色梯度渐变，，float数组表示，[0,0.75)为起始颜色所占比例，[0.75,1}为起止颜色渐变所占比例
        mSweepGradient = new SweepGradient(w / 2, h / 2, new int[]{mDarkColor, mLightColor}, new float[]{0.75f, 1});

        mTextSize = (getWidth() - mPaddingLeft - mPaddingRight - mScaleLength) / 6;

    }

    //初始化属性动画，毫秒针
    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, mRound);//每秒钟毫秒针转一圈，正好是360度
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());//设置线性执行动画
        animator.setRepeatCount(ValueAnimator.INFINITE);//设置无限循环

        //设置 值的更新监听器  即：值每次改变、变化一次,该方法就会被调用一次
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置动画监听，获取当前动画的值，重新计算指针转过的角度，然后重绘
                mMilliSecondDegree = (float) animation.getAnimatedValue();
                //毫秒数
//                milliSecondCount = (float) animation.getAnimatedValue() / 6;
                milliSecondCount = (float) animation.getAnimatedValue() / 3.6f;
                //秒针角度
                mSecondDegree = 6 * secondCount + (float) animation.getAnimatedValue() / 60;

                Log.d(TAG, "onAnimationUpdate: count\t\t" + count + "\t\tanimation.getAnimatedValue()\t\t\t" + animation.getAnimatedValue() +
                        "\t\tanimation.getAnimatedValue() / 60\t\t\t" + ((float) animation.getAnimatedValue() / 60));
                invalidate();
            }
        });

        //Animation类通过监听动画开始 / 结束 / 重复 / 取消时刻来进行一系列操作
        animator.addListener(new Animator.AnimatorListener() {
            //动画开始时执行
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            //动画结束时执行
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
            }

            //动画取消时执行
            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            //动画重复时执行
            @Override
            public void onAnimationRepeat(Animator animation) {
                //重复执行的时候强制把时间推进一秒，如果达到60秒的话，说明秒针已经转过一圈了，
                //把动画执行次数重置为0
                mMilliSecondDegree = 0;
                //秒针角度是毫秒计数的6倍
                mSecondDegree = 6 * count;
                count++;
                secondCount++;
                if (count >= 100) {
                    count = 0;
                }
                //变换分钟计数
                if (secondCount >= 60) {
                    secondCount = 0;
                    minuteCount++;
                }
                Log.d(TAG, "onAnimationRepeat: 毫秒：" + (int) milliSecondCount + "\t\t 秒：" + secondCount + "\t\t 分钟：" + minuteCount);
            }
        });
    }

    //初始化属性动画，毫秒针回归动画
    private void initBackAnimator() {
        backAnimator = ValueAnimator.ofFloat(mRound, 0);//每秒钟毫秒针转一圈，正好是360度
        backAnimator.setDuration(500);
        backAnimator.setInterpolator(new LinearInterpolator());//设置线性执行动画
        backAnimator.setRepeatCount(0);//设置设置无限循环

        //设置 值的更新监听器  即：值每次改变、变化一次,该方法就会被调用一次
        backAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //计算角度
                mSecondDegree = mBackDegree - (mRound - (float) animation.getAnimatedValue()) / (mRound / mBackDegree);
                mMilliSecondDegree = mMilliSecondBackDegree - (mRound - (float) animation.getAnimatedValue()) / (mRound / mMilliSecondBackDegree);

                Log.d(TAG, "mBackDegree: 需要回转的角度\t\t" + mBackDegree + "mSecondDegree: 秒针角度\t\t" +
                        mSecondDegree + "mMilliSecondDegree: 毫秒针角度\t\t" + mMilliSecondDegree);

                invalidate();
            }
        });

        //Animation类通过监听动画开始 / 结束 / 重复 / 取消时刻来进行一系列操作
        backAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //开始动画运行次数
                count = 0;

                //角度
                mMilliSecondDegree = 0;
                mSecondDegree = 0;
            }
        });
    }


    /**
     * 秒针
     */
    private void drawSecondNeedle() {
        mCanvas.save();
        //旋转
        mCanvas.rotate(mSecondDegree, getWidth() / 2, getHeight() / 2);
        mSecondHandPath.reset();

        mSecondHandPath.moveTo(getWidth() / 2, mPaddingTop + 0.12f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 - 0.05f * mRadius, mPaddingTop + 0.01f * mRadius);
        mSecondHandPath.lineTo(getWidth() / 2 + 0.05f * mRadius, mPaddingTop + 0.01f * mRadius);
        mSecondHandPath.close();
        mSecondHandPaint.setColor(mLightColor);

        mCanvas.drawPath(mSecondHandPath, mSecondHandPaint);
        mCanvas.restore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;

        drawScaleLine();
        drawSecondNeedle();
        drawMilliSecondCircle();
        drawMilliSecond();
        drawText();
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/20
     * @author lady_zhou
     * @Description 绘制文字
     */
    private void drawText() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);

        String minute;
        String second;
        String milliSecond;
        if (milliSecondCount >= 10) {
            milliSecond = String.valueOf((int) milliSecondCount);
        } else {
            milliSecond = "0" + (int) milliSecondCount;
        }

        if (secondCount >= 10) {
            second = String.valueOf((int) secondCount);
        } else {
            second = "0" + (int) secondCount;
        }

        if (minuteCount >= 10) {
            minute = String.valueOf((int) minuteCount);
        } else {
            minute = "0" + (int) minuteCount;
        }
        time = minute + ":" + second + "." + milliSecond;
        mTextPaint.setTextSize(mTextSize);

        mCanvas.drawText(time, getWidth() / 2, getHeight() / 2, mTextPaint);
        mCanvas.restore();
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 画毫秒针表盘
     */
    private void drawMilliSecondCircle() {
        //毫秒表盘
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);
        mCanvas.drawCircle(getWidth() / 2,
                getHeight() / 2 + 2f * mScaleLength,
                mDefaultPadding,
                mMillisecondCirclePaint);

        mCanvas.restore();

        //毫秒表盘圆心
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);
        mCanvas.drawCircle(getWidth() / 2,
                getHeight() / 2 + 2f * mScaleLength,
                mDefaultPadding * 0.1f,
                mMillisecondCirclePaint);

        mCanvas.restore();
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/13
     * @author lady_zhou
     * @Description 画圆弧刻度
     */
    private void drawScaleLine() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);

        mScaleArcRectF.set(mPaddingLeft + 1.5f * mScaleLength,
                mPaddingTop + 1.5f * mScaleLength,
                getWidth() - mPaddingRight - 1.5f * mScaleLength,
                getHeight() - mPaddingBottom - 1.5f * mScaleLength);

        if (isTrue) {
            mGradientMatrix.setRotate(mSecondDegree - 90, getWidth() / 2, getHeight() / 2);
            mSweepGradient.setLocalMatrix(mGradientMatrix);
            mScaleArcPaint.setShader(mSweepGradient);
        } else {
            mScaleArcPaint.setShader(null);
        }
        mCanvas.drawArc(mScaleArcRectF, 0, 360, false, mScaleArcPaint);

        //画背景色刻度线
        for (int i = 0; i < 200; i++) {
            mCanvas.drawLine(getWidth() / 2,
                    mPaddingTop + mScaleLength,
                    getWidth() / 2,
                    mPaddingTop + 2 * mScaleLength,
                    mScaleLinePaint);

            mCanvas.rotate(1.8f, getWidth() / 2, getHeight() / 2);
        }
        mCanvas.restore();

    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 画毫秒针
     */
    private void drawMilliSecond() {
        mCanvas.save();
        mCanvas.translate(mCanvasTranslateX, mCanvasTranslateY);

        mCanvas.rotate(mMilliSecondDegree, getWidth() / 2, getHeight() / 2 + 2f * mScaleLength);
        mCanvas.drawLine(getWidth() / 2,
                getHeight() / 2 + 2f * mScaleLength,
                getWidth() / 2,
                getHeight() / 2 + 1.25f * mScaleLength,
                mMilliSecondPaint);

        mCanvas.restore();
    }

    /**
     * @param isTrue : 是否开始
     * @return : void
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 开始计时
     */
    public void start(boolean isTrue) {
        this.isTrue = isTrue;
        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    /**
     * @param isTrue :  是否开始
     * @return : long
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 暂停计时
     */
    public long pause(boolean isTrue) {
        this.isTrue = isTrue;
        if (animator != null && animator.isRunning()) {
            long playTime = animator.getCurrentPlayTime();
            animator.cancel();
            mBackDegree = mSecondDegree;
            mMilliSecondBackDegree = mMilliSecondDegree;
            return playTime;
        }
        return 0;

    }

    /**
     * @param playTime :  开始的时间
     * @param isTrue   : 是否开始
     * @return : void
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 暂停后重新计时
     */
    public void restart(long playTime, boolean isTrue) {
        this.isTrue = isTrue;
        if (animator != null && !animator.isRunning()) {
            animator.setCurrentPlayTime(playTime);
            animator.start();
        }
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/19
     * @author lady_zhou
     * @Description 重置秒表状态
     */
    public void clean() {
        if (animator != null && !animator.isStarted() && !animator.isRunning()) {
            animator.end();

        }
        //时间计时归0
        minuteCount = 0;
        milliSecondCount = 0;
        secondCount = 0;

        initBackAnimator();

        if (backAnimator != null && !backAnimator.isStarted()) {
            backAnimator.start();
        }
    }

    public void stopAnimator() {
        if (animator != null) {
            animator.end();
        }
    }

    /**
     * @return : java.lang.String
     * @date 创建时间: 2018/12/25
     * @author lady_zhou
     * @Description 记录时间
     */
    public String recordTime() {
        if (animator != null && animator.isRunning()) {
            return time;
        }
        return null;
    }

}
