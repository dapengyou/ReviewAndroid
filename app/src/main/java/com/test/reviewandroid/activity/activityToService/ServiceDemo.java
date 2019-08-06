package com.test.reviewandroid.activity.activityToService;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @createTime: 2019/3/25
 * @author: lady_zhou
 * @Description:
 */
public class ServiceDemo extends Service {
    private static final String TAG = "ServiceDemo";
    private Timer mTimer;

    @Override
    public void onCreate() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        super.onCreate();
        Log.d(TAG, "方法：onCreate: 服务的onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        start();
        Log.d(TAG, "方法：onStartCommand: 开始服务");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * @return : void
     * @date 创建时间: 2019/3/25
     * @author lady_zhou
     * @Description 开始服务计数
     */
    private void start() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Log.d(TAG, "方法：start: " + i);
                }
            }
        }, 1000, 5000);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        bind();
        Log.d(TAG, "方法：onBind: 绑定服务");
        return new MyBinder();
    }

    private void bind() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                    for (int i = 0; i < 5; i++) {
//                        mListener.updateNumber(i);
                        Log.d(TAG, "方法：bind: " + i);

                    }
            }
        }, 1000, 5000);
    }

    public class MyBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public ServiceDemo startService() {
            return ServiceDemo.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "方法：onUnbind: 解绑服务");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "方法：onDestroy: 服务销毁");
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "方法：onRebind: 重新绑定");
        bind();
        super.onRebind(intent);
    }
}
