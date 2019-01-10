package com.test.reviewandroid.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2019/1/10
 * @Description activity与service通信
 */
public class ReviewServiceActivity extends AppCompatActivity {

    @BindView(R.id.et_time)
    EditText mEtTime;
    private Intent intent;
    private MyReceiver mMyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_service);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.bt_sure, R.id.bt_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_close:
                break;
            case R.id.bt_sure:

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
                    //进行授权
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {

                    intent = new Intent(ReviewServiceActivity.this, MyService.class);
                    intent.putExtra(MyService.DATA, Integer.parseInt(mEtTime.getText().toString()));
                    startService(intent);

                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
//        mMyReceiver = new MyReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("myReceiver");
//        // 3. 动态注册：调用Context的registerReceiver（）方法
//        registerReceiver(mMyReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stopService(intent);
//        unregisterReceiver(mMyReceiver);
    }
}
