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
public class ViewGroupB extends LinearLayout {
    private static final String TAG = "ViewEvent";

    public ViewGroupB(Context context) {
        super(context);
    }

    public ViewGroupB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "方法：dispatchTouchEvent: --------------viewGroupB----------");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：onTouchEvent: --------------viewGroupB----------");

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "方法：onInterceptTouchEvent: --------------viewGroupB----------");

        return super.onInterceptTouchEvent(ev);
    }
}
