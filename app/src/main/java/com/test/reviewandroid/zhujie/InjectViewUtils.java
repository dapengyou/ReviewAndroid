package com.test.reviewandroid.zhujie;

import android.app.Activity;

import java.lang.reflect.Field;

/**
 * @createTime: 2019-10-29
 * @author: lady_zhou
 * @Description:
 */
public class InjectViewUtils {
    public static void injectView(Activity activity) {
        if(null==activity) return;

        Class<? extends Activity> activityClass = activity.getClass();
        Field[] declareFields = activityClass.getDeclaredFields();
        for(Field field:declareFields){//获取Activity类里面声明的所有成员变量
            if (field.isAnnotationPresent(InjectView.class)){//找出标注了@InjectView的成员变量
                //解析InjectView   获取button  id
//                InjectView injectView = field.getAnnotations(InjectView.class);

            }

        }
    }
}
