package com.test.reviewandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import com.test.reviewandroid.R;

/**
 * @createTime: 2019-10-18
 * @author: lady_zhou
 * @Description:
 */
public class ClearDrawable extends Drawable {
    private Paint mPaint;
    private Bitmap mBgBitmap;

    public ClearDrawable(Context context, int width, int height) {
        super();
        init(context, width, height);
    }

    private void init(Context context, int width, int height) {
        mPaint = new Paint();
        Bitmap tempBgBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bg);
        mBgBitmap = Bitmap.createScaledBitmap(tempBgBitmap, width, height, true);

    }

    /**
     * @createTime: 2019-10-18
     * @author lady_zhou
     * @Description 核心方法
     */
    @Override
    public void draw(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawBitmap(mBgBitmap, 0, 0, mPaint);
    }

    /**
     * @createTime: 2019-10-18
     * @author lady_zhou
     * @Description 设置透明度
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();

    }

    /**
     * @createTime: 2019-10-18
     * @author lady_zhou
     * @Description 设置颜色过滤器
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();

    }

    /**
     * @createTime: 2019-10-18
     * @author lady_zhou
     * @Description 核心方法
     */
    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
