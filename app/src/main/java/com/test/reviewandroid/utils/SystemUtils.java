package com.test.reviewandroid.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @createTime: 2018/12/10
 * @author: lady_zhou
 * @Description:
 */
public class SystemUtils {

    /**
     * @param context     : 一个context
     * @param packageName : 要判断应用的包名
     * @return : boolean
     * @date 创建时间: 2018/12/10
     * @author lady_zhou
     * @Description 判断应用是否已经启动
     */
    private boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    /**
     * @param context      : 一个context
     * @param activityName : 要判断Activity
     * @return : boolean
     * @date 创建时间: 2018/12/10
     * @author lady_zhou
     * @Description 判断MainActivity是否活动
     */
    private boolean isMainActivityAlive(Context context, String activityName) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            // 注意这里的 topActivity 包含 packageName和className，可以打印出来看看
            if (info.topActivity.toString().equals(activityName) || info.baseActivity.toString().equals(activityName)) {
                Log.i(info.topActivity.getPackageName(), info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                return true;
            }
        }
        return false;
    }


    /**
     * @param mContext     : 一个context
     * @param activityName : 某Activity的名字
     * @return : boolean
     * @date 创建时间: 2018/12/10
     * @author lady_zhou
     * @Description 检测某Activity是否在当前Task的栈顶
     */
    private boolean isTopActivity(Context mContext, String activityName) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;
        if (runningTaskInfos != null) {
            cmpNameTemp = runningTaskInfos.get(0).topActivity.toString();
        }
        if (cmpNameTemp == null) {
            return false;
        }
        return cmpNameTemp.equals(activityName);
    }


    /**
     * @param mContext  : 一个context
     * @param className : 判断的服务名字
     * @return : boolean     true 在运行 false 不在运行
     * @date 创建时间: 2018/12/10
     * @author lady_zhou
     * @Description 用来判断服务是否运行
     */
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        //此处只在前30个中查找，大家根据需要调整
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static boolean isAppRunning(Context context, String name) {
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            ActivityManager am = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
            if (infos == null) {
                return false;
            } else {
                Iterator var4 = infos.iterator();

                ActivityManager.RunningAppProcessInfo info;
                do {
                    if (!var4.hasNext()) {
                        return false;
                    }

                    info = (ActivityManager.RunningAppProcessInfo)var4.next();
                } while(!info.processName.equals(name));

                return true;
            }
        }
    }
}
