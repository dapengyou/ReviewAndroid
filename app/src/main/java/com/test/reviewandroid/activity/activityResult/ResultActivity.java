package com.test.reviewandroid.activity.activityResult;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.reviewandroid.R;

public class ResultActivity extends AppCompatActivity {
    public static final int RESULT_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

}
