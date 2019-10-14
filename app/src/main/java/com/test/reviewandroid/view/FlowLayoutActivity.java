package com.test.reviewandroid.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.reviewandroid.R;

public class FlowLayoutActivity extends AppCompatActivity {
    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        mFlowLayout = findViewById(R.id.flowLayout);
        addView();
    }

    private void addView() {
        for (int i = 0; i < 50; i++) {
            //定义Button的布局
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            Button button = new Button(this);
            button.setText("祝你好运，一飞冲天");
            button.setLayoutParams(vlp);
            mFlowLayout.addView(button);
        }

        for (int i = 0; i < 50; i++) {
            //定义Button的布局
            ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            Button button = new Button(this);
            button.setText("我再试试");
            button.setLayoutParams(vlp);
            mFlowLayout.addView(button);
        }
    }
}
