package com.test.reviewandroid.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        //初始化自定义适配器
        mAdapter = new Adapter(fm);
        //绑定自定义适配器
        mViewPager.setAdapter(mAdapter);
    }
}
