package com.test.reviewandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.reviewandroid.activity.LineActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_activity:
                startActivity(new Intent(this,LineActivity.class));
                break;
        }
    }
}
