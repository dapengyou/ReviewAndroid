package com.test.reviewandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.test.reviewandroid.activity.AllAnimationActivity;
import com.test.reviewandroid.activity.LineActivity;
import com.test.reviewandroid.net.DownLoadApkActivity;
import com.test.reviewandroid.recycleView.userComplexLayout.CategoryActivity;
import com.test.reviewandroid.recycleView.userComplexLayout.CollapsibleActivity;
import com.test.reviewandroid.recycleView.userComplexLayout.ComplexLayoutActivity;
import com.test.reviewandroid.viewEvent.ViewEventActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermission();
        Log.d(TAG, getClass().getSimpleName() + "方法：onCreate: taskId:" + getTaskId() + "\thashcode:" + hashCode());
    }

    private void requestPermission() {
        //动态申请权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, getClass().getSimpleName() + "方法：onNewIntent:" + "taskId:" + getTaskId()+ "\thashcode:" + hashCode());
    }

    @OnClick({R.id.bt_activity, R.id.bt_components, R.id.bt_animation, R.id.bt_okhttp, R.id.bt_view_event,R.id.bt_recycleview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_activity:
                startActivity(new Intent(this, LineActivity.class));
                break;
            case R.id.bt_components:
                startActivity(new Intent(this, MainActivity.class));
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
            case R.id.bt_recycleview:
//                startActivity(new Intent(this, ComplexLayoutActivity.class));
//                startActivity(new Intent(this, CategoryActivity.class));
                startActivity(new Intent(this, CollapsibleActivity.class));

                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "方法：onStart: One");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "方法：onResume: One");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "方法：onPause: One");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "方法：onStop: One");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "方法：onDestroy: One");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "方法：onRestart: One");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "方法：onSaveInstanceState: one");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "方法：onRestoreInstanceState: one");
    }
}
