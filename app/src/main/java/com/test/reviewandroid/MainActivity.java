package com.test.reviewandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.reviewandroid.activity.AllAnimationActivity;
import com.test.reviewandroid.activity.LineActivity;
import com.test.reviewandroid.activity.fourComponents.ReviewServiceActivity;
import com.test.reviewandroid.net.DownLoadApkActivity;
import com.test.reviewandroid.net.HttpResumeActivity;
import com.test.reviewandroid.net.OkHttpActivity;
import com.test.reviewandroid.viewEvent.ViewEventActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermission();
    }

    private void requestPermission() {
        //动态申请权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @OnClick({R.id.bt_activity, R.id.bt_components, R.id.bt_animation, R.id.bt_okhttp,R.id.bt_view_event})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_activity:
                startActivity(new Intent(this, LineActivity.class));
                break;
            case R.id.bt_components:
                startActivity(new Intent(this, ReviewServiceActivity.class));
                break;
            case R.id.bt_animation:
                startActivity(new Intent(this, AllAnimationActivity.class));
                break;
            case R.id.bt_okhttp:
//                startActivity(new Intent(this, HttpResumeActivity.class));
//                startActivity(new Intent(this, OkHttpActivity.class));
                startActivity(new Intent(this, DownLoadApkActivity.class));
                break;
                case R.id.bt_view_event:
                startActivity(new Intent(this, ViewEventActivity.class));
                break;

        }
    }
}
