package com.test.reviewandroid.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @createTime: 2019-10-29
 * @author: lady_zhou
 * @Description: 事件类型
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    Class listener();

    String setOnClickListener();

    String methodName();
}
