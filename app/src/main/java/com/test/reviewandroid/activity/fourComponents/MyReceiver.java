package com.test.reviewandroid.activity.fourComponents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.test.reviewandroid.MainActivity;
import com.test.reviewandroid.utils.SystemUtils;

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION= "action";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
//        if(intent.getAction().equals("myReceiver")) {
            Log.d("MyReceiver", "收到广播了");
            showActivity(context);
//        }
    }

    private void showActivity(Context context) {
        //判断app进程是否存活
        if (SystemUtils.isAppRunning(context, "com.test.reviewandroid")) {

            /**
             * 如果存活的话，就直接启动AlarmActivity，但要考虑一种情况，就是app的进程虽然仍然在
             * 但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
             * AlarmActivity,再按Back键就不会返回MainActivity了。所以在启动
             * AlarmActivity前，要先启动MainActivity。
             */

            Log.i("MyReceiver", "the app process is alive");
            Intent mainIntent = new Intent(context, MainActivity.class);

            /**
             * 将MainAtivity的launchMode设置成SingleTask, 或者在下面flag中加上Intent.FLAG_CLEAR_TOP,
             * 如果Task栈中有MainActivity的实例，就会把它移到栈顶，把在它之上的Activity都清理出栈，
             * 如果Task栈不存在MainActivity实例，则在栈顶创建*
             */
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Intent  alarmIntent = new Intent(context, AlarmActivity.class);
//            alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            context.startActivity(alarmIntent);
            Intent[] intents = {mainIntent, alarmIntent};

            context.startActivities(intents);
        } else {

            Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.test.reviewandroid");
            launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(launchIntent);
        }
    }
}
