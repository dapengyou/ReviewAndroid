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
public class ViewB extends View {
    private static final String TAG = "ViewEvent";

    public ViewB(Context context) {
        super(context);
    }

    public ViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：onTouchEvent: --------viewB---------");
//        super.onTouchEvent(event);
        return  false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(TAG, "方法：dispatchTouchEvent: -------------viewB-----------");
        return super.dispatchTouchEvent(event);
    }
}
