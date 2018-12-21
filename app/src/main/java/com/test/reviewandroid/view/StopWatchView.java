package com.test.reviewandroid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.test.reviewandroid.R;

/**
 * @createTime: 2018/12/19
 * @author: lady_zhou
 * @Description:
 */
public class StopWatchView extends View {


    private int mainScaleColor; //主刻度颜色
    private int otherScaleColor; //其他刻度颜色
    private int thirdScaleColor; //最小刻度颜色
    private int bigPointerColor; //秒针针颜色
    private int smallPointerColor; //毫秒针颜色
    private int smallDialColor; //小圆盘颜色
    private int circleColor; //外圆颜色
    private Paint paint; //画笔
    private Bitmap background; //背景图片
    private float viewRadius; //半径大小
    private float oneUnit; //一个单位长度
    private float bigPointerDegree; //秒针转过的角度
    private float smallPointerDegree; //毫秒针转过的角度
    private int count; //动画执行次数
    private ValueAnimator animator; //执行的属性动画

    public StopWatchView(Context context) {
        super(context, null);
    }

    public StopWatchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        initPaint();
        initAnimator();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //去长度和宽度的最小值作为尺寸，保证view是一个正方形
        int specSize = Math.min(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        setMeasuredDimension(specSize, specSize);
        if (viewRadius == - 1) //如果未设置半径，把半径设为view的长度的一半
            viewRadius = specSize / 2;
        oneUnit = (float) (viewRadius / 453.0); //设置一个单位的大小
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        drawBackground(canvas);
        drawBigScale(canvas);
//        drawNumber(canvas);

        canvas.save();
        canvas.translate(0, - oneUnit * 134);
        drawSmallDial(canvas);
        drawSmallPointer(canvas);
        canvas.restore();

        canvas.save();
        drawBigPointer(canvas);
        canvas.restore();

    }

    //初始化控件
    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StopWatchView);
        viewRadius = ta.getDimension(R.styleable.StopWatchView_radius, -1);
        circleColor = ta.getColor(R.styleable.StopWatchView_circle_color, Color.WHITE);
        mainScaleColor = ta.getColor(R.styleable.StopWatchView_main_scale_color, Color.WHITE);
        otherScaleColor = ta.getColor(R.styleable.StopWatchView_other_scale_color, Color.WHITE);
        thirdScaleColor = ta.getColor(R.styleable.StopWatchView_third_scale_color, Color.WHITE);
        bigPointerColor = ta.getColor(R.styleable.StopWatchView_big_pointer_color, Color.WHITE);
        smallDialColor = ta.getColor(R.styleable.StopWatchView_small_dial_color, Color.WHITE);
        smallPointerColor = ta.getColor(R.styleable.StopWatchView_small_pointer_color, Color.WHITE);
        ta.recycle();
        background = BitmapFactory.decodeResource(getResources(), Color.WHITE);

        bigPointerDegree = 0;
        smallPointerDegree = 0;
        count = 0;


    }

    //初始化画笔
    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    //初始化属性动画
    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, 360);//每秒钟毫秒针转一圈，正好是360度
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());//设置线性执行动画
        animator.setRepeatCount(ValueAnimator.INFINITE);//设置无限循环
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //设置动画监听，获取当前动画的值，重新计算指针转过的角度，然后重绘
                smallPointerDegree = (float) animation.getAnimatedValue();
                bigPointerDegree = 6 * count + (float) animation.getAnimatedValue() / 60;
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("TAG", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("TAG", "onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("TAG", "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //重复执行的时候强制把时间推进一秒，如果达到60秒的话，说明秒针已经转过一圈了，
                //把动画执行次数重置为0
                smallPointerDegree = 0;
                bigPointerDegree = 6 * count;
                count++;
                if (count >= 60) {
                    count = 0;
                }
            }
        });
    }

    //    计算宽度
    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
            viewRadius = - 1;
        } else {
            //若长度为wrap_content,如果未设置半径，把长度设为200，否则长度为半径的两倍
            if (specMode == MeasureSpec.AT_MOST) {
                if (viewRadius == - 1) {
                    result = 200;
                } else {
                    result = (int) (viewRadius * 2);
                }
            }
        }
        return result;
    }

    //    计算高度
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
            viewRadius = - 1;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                if (viewRadius == - 1) {
                    result = 200;
                } else {
                    result = (int) (viewRadius * 2);
                }
            }
        }
        return result;
    }

    //画背景
    private void drawBackground(Canvas canvas) {
        int radiusInt = (int) viewRadius;
        Rect rect = new Rect(- radiusInt, - radiusInt, radiusInt, radiusInt);
        canvas.drawBitmap(background, null, rect, null);
        paint.setStrokeWidth(4 * oneUnit);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, oneUnit * 261, paint);
    }

    //画大刻度
    private void drawBigScale(Canvas canvas) {
        paint.setColor(mainScaleColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(oneUnit * 3);
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(0, - oneUnit * 254, 0, - oneUnit * 238, paint);
            canvas.rotate(30);
        }


        paint.setColor(otherScaleColor);
        paint.setStrokeWidth(oneUnit * 2);
        for (int i = 0; i < 60; i++) {
            if (i % 5 != 0) {
                canvas.drawLine(0, - oneUnit * 254, 0, - oneUnit * 244, paint);
            }
            canvas.rotate(6);
        }

        paint.setColor(thirdScaleColor);
        for (int i = 0; i < 300; i++) {
            if (i % 5 != 0) {
                canvas.drawLine(0, - oneUnit * 254, 0, - oneUnit * 248, paint);
            }
            canvas.rotate((float) 1.2);
        }
    }

/*    private void drawNumber(Canvas canvas) {
        paint.setColor(mainScaleColor);
        paint.setTextSize(oneUnit * 20);
        for (int i = 0; i < 12; i++) {
            canvas.drawText(5 * i + "", (float) (Math.sin(i * Math.PI / 6) * oneUnit * 220), (float) (- Math.cos(i * Math.PI / 6) * oneUnit * 220), paint);
//            canvas.rotate(30);
        }
    }*/

    //画小圆盘和圆盘上的刻度
    private void drawSmallDial(Canvas canvas) {
        paint.setColor(smallDialColor);
        paint.setStrokeWidth(oneUnit * 2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0, 0, oneUnit * 52, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(oneUnit);
        for (int i = 0; i < 24; i++) {
            canvas.drawLine(0, - oneUnit * 51, 0, - oneUnit * 46, paint);
            canvas.rotate(15);
        }
    }

    //画秒针
    private void drawBigPointer(Canvas canvas) {
        paint.setColor(bigPointerColor);
        paint.setStrokeWidth(0);
        canvas.drawCircle(0, 0, (float) (oneUnit * 9.5), paint);

        Path path = new Path();
        path.moveTo((float) (- oneUnit * 4.5), oneUnit * 34);
        path.lineTo((float) (oneUnit * 4.5), oneUnit * 34);
        path.lineTo((float) (oneUnit * 2.5), oneUnit * - 254);
        path.lineTo((float) (- oneUnit * 2.5), oneUnit * - 254);
        path.close();
        canvas.rotate(bigPointerDegree);
        canvas.drawPath(path, paint);
    }

    //画毫秒针
    private void drawSmallPointer(Canvas canvas) {
        paint.setColor(smallPointerColor);
        canvas.drawCircle(0, 0, oneUnit * 5, paint);

        Path path = new Path();
        path.moveTo((float) (- oneUnit * 2.5), 0);
        path.lineTo((float) (oneUnit * 2.5), 0);
        path.lineTo(0, - oneUnit * 46);
        path.close();
        canvas.rotate(smallPointerDegree);
        canvas.drawPath(path, paint);
    }


    //开始计时
    public void start() {
        if (animator != null && !animator.isStarted())
            animator.start();

    }

    //暂停计时
    public long pause() {
        if (animator != null && animator.isRunning()) {
            long playTime = animator.getCurrentPlayTime();
            animator.cancel();
            return playTime;
        }
        return 0;
    }

    //暂停后重新计时
    public void restart(long playTime) {
        if (animator != null && !animator.isRunning()) {
            animator.setCurrentPlayTime(playTime);
            animator.start();
        }
    }

    //重置秒表状态
    public void clean() {
        if (animator != null && !animator.isStarted() && !animator.isRunning()) {
            animator.end();

            count = 0;
            bigPointerDegree = 0;
            smallPointerDegree = 0;
        }
    }

}
