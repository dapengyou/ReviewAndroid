package com.test.reviewandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.test.reviewandroid.R;
import com.test.reviewandroid.activity.fourComponents.ReviewServiceActivity;
import com.test.reviewandroid.view.ClockViewActivity;
import com.test.reviewandroid.view.FinishActivity;
import com.test.reviewandroid.view.ImageViewActivity;
import com.test.reviewandroid.view.MyViewGroupActivity;
import com.test.reviewandroid.view.SearchActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2019/1/15
 * @Description
 */
public class AllAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_animation);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_view, R.id.bt_image, R.id.bt_search, R.id.bt_finish, R.id.bt_myself})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_view:
                startActivity(new Intent(this, ClockViewActivity.class));
                break;
            case R.id.bt_image:
                startActivity(new Intent(this, ImageViewActivity.class));
                break;
            case R.id.bt_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.bt_finish:
                startActivity(new Intent(this, FinishActivity.class));
                break;
            case R.id.bt_myself:
                startActivity(new Intent(this, MyViewGroupActivity.class));
                break;
        }
    }
}
