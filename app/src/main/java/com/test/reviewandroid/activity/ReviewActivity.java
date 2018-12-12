package com.test.reviewandroid.activity;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewActivity extends AppCompatActivity {
    private final String TAG = ReviewActivity.class.getSimpleName();
    private static final String TURN = "turn";
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.tv_show)
    TextView mTvShow;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: " + "调用onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
//        mTvShow.setText(savedInstanceState.getString(TURN));
        mTvShow.setText(mEtName.getText().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: 调用onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: 调用onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: 调用onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: 调用onRestart");

        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: 调用onPause");

        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: 调用onStop");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: 调用onDestroy");

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(TAG, "onSaveInstanceState: 调用onSaveInstanceState");

        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(TURN, mEtName.getText().toString());
    }

    /**
     * @date 创建时间: 2018/12/1
     * @author  lady_zhou
     * @Description  activity不被重建
     * @param newConfig :
     * @return : void
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: 切屏防止activity调用onSaveInstanceState");
        super.onConfigurationChanged(newConfig);
    }

    @OnClick(R.id.bt_sure)
    public void onViewClicked() {
        mTvShow.setText(mEtName.getText().toString());
    }

}
