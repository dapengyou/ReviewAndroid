package com.test.reviewandroid.activity.activityToActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.reviewandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        ButterKnife.bind(this);

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
}
