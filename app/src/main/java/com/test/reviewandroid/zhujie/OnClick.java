package com.test.reviewandroid.zhujie;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @createTime: 2019-10-29
 * @author: lady_zhou
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Event(listener = View.OnClickListener.class, setOnClickListener = "setOnClickListener", methodName = "onClick")
public @interface OnClick {
    int[] value();
}
