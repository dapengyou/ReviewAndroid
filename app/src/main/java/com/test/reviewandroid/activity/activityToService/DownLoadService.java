package com.test.reviewandroid.activity.activityToService;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @createTime: 2019/1/16
 * @author: lady_zhou
 * @Description:
 */
public class DownLoadService extends Service {
    private final String TAG = getClass().getSimpleName();
    //进度条的最大值
    public static final int MAX_PROGRESS = 100;

    // 进度条的进度值
    private int progress = 0;

    private OnProgressListener onProgressListener;

    /**
     * @param intent :  返回一个Binder对象
     * @return : android.os.IBinder
     * @date 创建时间: 2019/1/16
     * @author lady_zhou
     * @Description
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        startDownLoad();
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public DownLoadService startService() {
            return DownLoadService.this;
        }
    }

    /**
     * @return : int 下载进度
     * @date 创建时间: 2019/1/16
     * @author lady_zhou
     * @Description 增加get()方法，供Activity调用
     */
    public int getProgress() {
        return progress;
    }

    private Thread mThread;

    /**
     * @return : void
     * @date 创建时间: 2019/1/16
     * @author lady_zhou
     * @Description 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad() {
        mThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    progress += 5;
                    if (Thread.currentThread().isInterrupted()) {
                        Log.d(TAG, "方法：run: \t\t中断线程");
                        break;
                    }
                    //进度发生变化通知调用方
                    if (onProgressListener != null) {
                        onProgressListener.onProgress(progress);
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    Log.d(TAG, "方法：run: 线程\t\t" + progress);
                }
            }
        });
        mThread.start();
    }

    /**
     * @author lady_zhou
     * @createTime: 2019/1/16
     * @Description 更新进度的回调接口
     */
    public interface OnProgressListener {
        void onProgress(int progress);
    }

    /**
     * @param onProgressListener :
     * @return : void
     * @date 创建时间: 2019/1/16
     * @author lady_zhou
     * @Description 注册回调接口的方法，供外部调用
     */
    public void setOnProgressListener(OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "方法：onUnbind: \t\t解绑了");
        mThread.interrupt();//中断线程

        return super.onUnbind(intent);
    }
}
