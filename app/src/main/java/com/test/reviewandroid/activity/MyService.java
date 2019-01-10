package com.test.reviewandroid.activity;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @createTime: 2018/12/7
 * @author: lady_zhou
 * @Description:
 */
public class MyService extends Service {
    private static final String TAG = "MyService";
    public static final String DATA = "data";
    private int data;
    private Calendar mCalendar;
    private Timer mTimer;
    private int minute;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            data = intent.getIntExtra(DATA, 1);
        }
        if (mTimer == null) {
            mTimer = new Timer();
        }
        startRemind(data);
        return super.onStartCommand(intent, flags, startId);
    }

    private void startRemind(final int data) {
        //得到日历实例，主要是为了下面的获取时间
        mCalendar = Calendar.getInstance();
        minute = mCalendar.get(Calendar.MINUTE);
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mCalendar = Calendar.getInstance();
                if (mCalendar.get(Calendar.MINUTE) == (minute + data)) {
                    Log.d(TAG, "发送广播了");
                    Intent intent = new Intent("myReceiver");
                    sendBroadcast(intent);
                    mTimer.cancel();
                    mTimer = null;
                } else {
                    Log.d(TAG, "现在时间:" + mCalendar.get(Calendar.MINUTE) + "需要时间" + (minute + data) + "还没到时间");

                }
            }
        }, 1000, 1000);


    }

}
