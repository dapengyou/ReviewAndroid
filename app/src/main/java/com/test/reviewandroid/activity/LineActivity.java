package com.test.reviewandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.reviewandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2018/12/4
 * @Description Activity学习线路
 */
public class LineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_activity_livelift, R.id.tv_activity_fragment, R.id.tv_activity_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_livelift:
                startActivity(new Intent(this, ReviewActivity.class));
                break;
            case R.id.tv_activity_fragment:
                break;
            case R.id.tv_activity_service:
                startActivity(new Intent(this, ReviewServiceActivity.class));
                break;
        }
    }
}