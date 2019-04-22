package com.test.reviewandroid.activity.activityToActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.test.reviewandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {
    private static final String TAG = "activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

        Log.d(TAG, "方法：onCreate: One");

    }

    @OnClick(R.id.bt_to_activity)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt("age", 19);
        bundle.putString("name", "雷东宝");

        Intent intent = new Intent(OneActivity.this, SecondActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
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
}
