package com.test.reviewandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends AppCompatActivity {

    @BindView(R.id.customer_iamge_view)
    CustomerImageView mCustomerIamgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
    }
}
