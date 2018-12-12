package com.test.reviewandroid.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.test.reviewandroid.R;
import com.test.reviewandroid.bean.Music;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                intent = new Intent(ReviewServiceActivity.this, MyService.class);
                intent.putExtra(MyService.DATA, Integer.parseInt(mEtTime.getText().toString()));
                startService(intent);
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
