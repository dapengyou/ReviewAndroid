package com.test.reviewandroid.activity.activityToActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends Activity {
    private static final String TAG = "activity";
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_age)
    TextView mTvAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        Log.d(TAG, "方法：onCreate: Second");
        //用getIntent()方法获取intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);

        mTvName.setText("传来的名字是：\t" + name);
        mTvAge.setText("传来的年龄是：\t" + age);
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
}
