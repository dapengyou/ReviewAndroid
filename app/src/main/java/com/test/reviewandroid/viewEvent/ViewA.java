package com.test.reviewandroid.viewEvent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @createTime: 2019/4/2
 * @author: lady_zhou
 * @Description:
 */
public class ViewA extends View {
    private static final String TAG = "ViewEvent";

    public ViewA(Context context) {
        super(context);
    }

    public ViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：onTouchEvent: ---------viewA------");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：dispatchTouchEvent: ---------viewA------");

        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "dispatchTouchEvent ACTION_DOWN");

                //如果你觉得需要拦截
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "dispatchTouchEvent ACTION_MOVE");

                //如果你觉得需要拦截
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "dispatchTouchEvent ACTION_UP");

                //如果你觉得需要拦截
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
