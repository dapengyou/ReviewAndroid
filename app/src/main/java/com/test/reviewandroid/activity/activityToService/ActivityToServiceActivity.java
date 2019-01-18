package com.test.reviewandroid.activity.activityToService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2019/1/17
 * @Description Activity与Service同信（使用的绑定服务）
 */
public class ActivityToServiceActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.bt_download)
    Button mBtDownload;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;

    //绑定服务第一步：  先创建服务中的binder对象
    private DownLoadService.MyBinder mMyBinder = null;
    private DownLoadService mDownLoadService = null;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_service);
        ButterKnife.bind(this);

        mIntent = new Intent(ActivityToServiceActivity.this, DownLoadService.class);
    }

    @OnClick(R.id.bt_download)
    public void onViewClicked() {
        bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //绑定服务第二步：  获取到DownLoadService返回的binder对象,并开启服务
            mDownLoadService = ((DownLoadService.MyBinder) service).startService();

            //注册回调接口来接收下载进度的变化
            mDownLoadService.setOnProgressListener(new DownLoadService.OnProgressListener() {

                @Override
                public void onProgress(int progress) {
                    mProgressbar.setProgress(progress);
                    if (progress == 100) {
                        unbindService(mServiceConnection);
                        Log.d(TAG, "方法：onProgress: \t\t解绑服务");
                    }
                    Log.d(TAG, "方法：onProgress: \t\t" + progress);

                }
            });


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownLoadService != null) {
            unbindService(mServiceConnection);
        }
    }
}
