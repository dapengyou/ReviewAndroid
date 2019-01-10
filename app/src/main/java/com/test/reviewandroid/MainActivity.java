package com.test.reviewandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.test.reviewandroid.activity.LineActivity;
import com.test.reviewandroid.view.ClockViewActivity;
import com.test.reviewandroid.view.ImageViewActivity;
import com.test.reviewandroid.view.SearchActivity;

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

    @OnClick({R.id.bt_activity, R.id.bt_view, R.id.bt_image,R.id.bt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_activity:
                startActivity(new Intent(this, LineActivity.class));
                break;
            case R.id.bt_view:
                startActivity(new Intent(this, ClockViewActivity.class));
                break;
            case R.id.bt_image:
                startActivity(new Intent(this, ImageViewActivity.class));
                break;
                case R.id.bt_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
        }
    }
}
