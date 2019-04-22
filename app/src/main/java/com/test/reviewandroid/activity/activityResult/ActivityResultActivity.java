package com.test.reviewandroid.activity.activityResult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.test.reviewandroid.R;

public class ActivityResultActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityresult);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == ResultActivity.RESULT_CODE) {
//            hobby = data.getStringExtra(ResultActivity.KEY_HOBBY);
//            if (!TextUtils.isEmpty(hobby)) {
//                mTvInfoHobby.setText(hobby);
//            } else {
//                mTvInfoHobby.setText("");
//            }

        }
    }
}
