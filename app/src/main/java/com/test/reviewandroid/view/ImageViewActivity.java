package com.test.reviewandroid.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends AppCompatActivity {
    @BindView(R.id.view_pageer)
    ViewPager mViewPageer;

//    @BindView(R.id.customer_iamge_view)
//    CustomerImageView mCustomerIamgeView;

    //设置viewpager数据源
    private int[] mImgs = new int[]{R.mipmap.icon_beijing, R.mipmap.ic_launcher, R.mipmap.icon_alarm};
    private CustomerImageView[] mCustomerImageViews = new CustomerImageView[mImgs.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        mViewPageer.setAdapter(new PagerAdapter() {
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                CustomerImageView customerImageView = new CustomerImageView(getApplicationContext());
                customerImageView.setImageResource(mImgs[position]);
                container.addView(customerImageView);
                mCustomerImageViews[position] = customerImageView;

                return customerImageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mCustomerImageViews[position]);
            }

            @Override
            public int getCount() {
                return mCustomerImageViews.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
    }
}
