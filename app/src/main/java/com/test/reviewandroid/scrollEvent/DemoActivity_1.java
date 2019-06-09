package com.test.reviewandroid.scrollEvent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.test.reviewandroid.R;

public class DemoActivity_1 extends AppCompatActivity {
    private static final String TAG = "activity";
    private HorizontalScrollViewEX mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_1);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = findViewById(R.id.container);
//        final int screenWidth =
        for(int i=0;i<3;i++){
//            ViewGroup layout  = inflater.inflate(R.layout.)
        }
    }
}
