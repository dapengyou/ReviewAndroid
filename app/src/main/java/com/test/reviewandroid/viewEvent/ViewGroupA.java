package com.test.reviewandroid.viewEvent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @createTime: 2019/4/2
 * @author: lady_zhou
 * @Description:
 */
public class ViewGroupA extends LinearLayout {
    private static final String TAG = "ViewEvent";

    public ViewGroupA(Context context) {
        super(context);
    }

    public ViewGroupA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "方法：dispatchTouchEvent: --------------viewGroupA----------");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：onTouchEvent: --------------viewGroupA----------");

        return false;
    }

    /**
     * @date 创建时间: 2019/4/2
     * @author  lady_zhou
     * @Description 拦截
     * @param ev :
     * @return : boolean  f
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "方法：onInterceptTouchEvent: --------------viewGroupA----------");

        return true;
    }
}
