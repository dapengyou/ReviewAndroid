package com.test.reviewandroid.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @createTime: 2019-10-29
 * @author: lady_zhou
 * @Description: InjectView用于注入view，用来代替findViewId方法
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int getId();//用来指定findViewId的值
}

