package com.test.reviewandroid.activity.activityToService;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceDemoActivity extends AppCompatActivity {
    private static final String TAG = "ServiceDemoActivity";
    private static final String KEY = "data";
    @BindView(R.id.bt_bind)
    Button mBtBind;
    private Intent mIntent;
    //绑定服务第一步：  先创建服务中的binder对象
    private ServiceDemo mServiceDemo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_demo);
        ButterKnife.bind(this);

        mIntent = new Intent(this, ServiceDemo.class);

    }

    @OnClick({R.id.bt_start, R.id.bt_stop, R.id.bt_bind, R.id.bt_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                Intent startIntent = new Intent(this, ServiceDemo.class);
                startService(startIntent);
//                startService(mIntent);
                break;
            case R.id.bt_stop:
                Log.d(TAG, "方法：onViewClicked: 点击了停止服务");
                Intent stopIntent = new Intent(this, ServiceDemo.class);
                stopService(stopIntent);
//                stopService(mIntent);
                break;
            case R.id.bt_bind:
                bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.bt_unbind:
                if (mServiceDemo != null) {
                    unbindService(mServiceConnection);
                    mBtBind.setText("绑定服务");
                }
                break;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //绑定服务第二步：  获取到DownLoadService返回的binder对象,并开启服务
            mServiceDemo = ((ServiceDemo.MyBinder) service).startService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        if (mServiceDemo != null) {
            unbindService(mServiceConnection);
        }
        super.onDestroy();
    }
}
