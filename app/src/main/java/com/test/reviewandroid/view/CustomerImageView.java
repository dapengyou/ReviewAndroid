package com.test.reviewandroid.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * @createTime: 2018/12/29
 * @author: lady_zhou
 * @Description:
 */
public class CustomerImageView extends android.support.v7.widget.AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private static final String TAG = "CustomerImageView";

    private boolean mOnce;
    private float mInitScale;//初始化时缩放的值，最小值
    private float mMidScale;//双击放小值到达的值
    private float mMaxScale;//放大的最大值

    private Matrix mScaleMatrix;
    private ScaleGestureDetector mScaleGestureDetector;//捕获多点触控的比例

    /*自由移动*/
    private int mLastPointerCount; //记录上一次多点触控的数量
    //记录中心的位置
    private float mLastX;
    private float mLastY;

    private int mTouchSlop;
    private boolean isCanDrag;//是否可以移动
    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBootom;

    public CustomerImageView(Context context) {
        this(context, null);
    }

    public CustomerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d(TAG, "CustomerImageView: ");
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//拿到可以作比较的值

    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/29
     * @author lady_zhou
     * @Description 当view加载在window上时执行此方法 ①
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册onGlobalLayout
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        Log.d(TAG, "onAttachedToWindow: ");
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/29
     * @author lady_zhou
     * @Description 当view从window中脱离时执行此方法  ①
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除onGlobalLayout
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Log.d(TAG, "onDetachedFromWindow: ");
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/29
     * @author lady_zhou
     * @Description 监听图片加载完成  ②
     */
    @Override
    public void onGlobalLayout() {
        Log.d(TAG, "onGlobalLayout: ");
        if (!mOnce) {
            //获取控件宽高（一般来说是屏幕的宽和高）
            int width = getWidth();
            int height = getHeight();
            //得到图片  以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }

            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();

            float scale = 1.0f;//缩放倍数

            //如果图片的宽度大于控件的宽,但是高小于控件的高,我们将其缩小
            if (drawableWidth > width && drawableHeight < height) {
                scale = width * 1.0f / drawableWidth;
            }
            //如果图片的宽度小于控件的宽,但是高大于控件的高,我们将其缩小
            if (drawableWidth < width && drawableHeight > height) {
                scale = height * 1.0f / drawableHeight;
            }
            //如果图片的宽高都大于控件的宽高，或者图片的宽高都小于控件的宽高，取缩放最小值
            if ((drawableWidth > width && drawableHeight > height) || (drawableWidth < width && drawableHeight < height)) {
                scale = Math.min(width * 1.0f / drawableWidth, height * 1.0f / drawableHeight);
            }

            //得到了初始化时缩放的比例
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            //将图片移动至控件的中心
            int dx = getWidth() / 2 - drawableWidth / 2;
            int dy = getHeight() / 2 - drawableWidth / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);//在中心点缩放
            setImageMatrix(mScaleMatrix);
            mOnce = true;
        }
    }

    /**
     * @return : float
     * @date 创建时间: 2018/12/29
     * @author lady_zhou
     * @Description 获取当前图片的缩放的值  ③
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /*多点触控 ③*/
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();//捕获的缩放值

        if (getDrawable() == null) {//没有图片
            return true;
        }
        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f) || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }

            //缩放
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            //检测
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);
        }
        return true;
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/2
     * @author lady_zhou
     * @Description 在缩放的时候进行边界控制以及我们的位置的控制  ④
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        //缩放时进行边界检测防止出现白边
        if (rectF.width() >= width) {
            if (rectF.left > 0) {//和左边有空隙
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        //如果宽度或者高度小于控件的宽或高，让其居中
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }
        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * @return : android.graphics.RectF
     * @date 创建时间: 2019/1/2
     * @author lady_zhou
     * @Description 获得图片放大缩小变化以后的宽和高， 以及left，right，top，bottom  ④
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rectF.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    //③
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    //③
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    /*触摸 */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);//拿到手指触控的坐标操作  ③

        //多点触控的中心点   ⑤
        float x = 0;
        float y = 0;
        int pointerCount = event.getPointerCount();//拿到多点触控的数量
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }
                if (isCanDrag) {
                    RectF rectF = getMatrixRectF();
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBootom = true;
                        //如果宽度小于控件的宽度，不允许横向移动
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        //如果高度小于控件的高度，不允许纵向移动
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBootom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderAndCenterWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }

                    mLastX = x;
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_CANCEL://手指抬起
                mLastPointerCount = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * @param dx :
     * @param dy :
     * @return : boolean
     * @date 创建时间: 2019/1/2
     * @author lady_zhou
     * @Description 判断是否是move  ⑤
     */
    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/2
     * @author lady_zhou
     * @Description 移动时的边界检查
     */
    private void checkBorderAndCenterWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }

        if (rectF.top > 0 && isCheckTopAndBootom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBootom) {
            deltaY = height - rectF.bottom;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

}
