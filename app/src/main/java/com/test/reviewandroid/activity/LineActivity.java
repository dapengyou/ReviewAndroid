package com.test.reviewandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.test.reviewandroid.MainActivity;
import com.test.reviewandroid.R;
import com.test.reviewandroid.activity.activityToActivity.OneActivity;
import com.test.reviewandroid.activity.activityToFragment.ActivityToFragmentActivity;
import com.test.reviewandroid.activity.activityToService.ActivityToServiceActivity;
import com.test.reviewandroid.activity.activityToService.ServiceDemo;
import com.test.reviewandroid.activity.activityToService.ServiceDemoActivity;
import com.test.reviewandroid.activity.fourComponents.ReviewServiceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2018/12/4
 * @Description Activity学习线路
 */
public class LineActivity extends AppCompatActivity {
    private static final String TAG = "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_activity);
        ButterKnife.bind(this);
        Log.d(TAG, getClass().getSimpleName() + "方法：onCreate: taskId:" + getTaskId());

    }

    @OnClick({R.id.tv_activity_livelift, R.id.tv_activity_fragment, R.id.tv_activity_service, R.id.tv_activity_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_livelift:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_activity_activity:
                startActivity(new Intent(this, OneActivity.class));
                break;
            case R.id.tv_activity_fragment:
                startActivity(new Intent(this, ActivityToFragmentActivity.class));
                break;
            case R.id.tv_activity_service:
//                startActivity(new Intent(this, ActivityToServiceActivity.class));
                startActivity(new Intent(this, ServiceDemoActivity.class));
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "方法：onStart: Second");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "方法：onResume: Second");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "方法：onPause: Second");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "方法：onStop: Second");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "方法：onDestroy: Second");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "方法：onSaveInstanceState: second");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "方法：onRestoreInstanceState: second");
    }
}
