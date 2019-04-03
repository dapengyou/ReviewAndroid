package com.test.reviewandroid.viewEvent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewEventActivity extends AppCompatActivity {
    private static final String TAG = "ViewEvent";

    @BindView(R.id.view_a)
    ViewA mViewA;
    @BindView(R.id.view_b)
    ViewB mViewB;
    @BindView(R.id.view_group_b)
    ViewGroupB mViewGroupB;
    @BindView(R.id.view_group_a)
    ViewGroupA mViewGroupA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.view_a, R.id.view_b, R.id.view_group_b, R.id.view_group_a})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_a:
                break;
            case R.id.view_b:
                break;
            case R.id.view_group_b:
                break;
            case R.id.view_group_a:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent:-------------MainActivity------------ ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent:-------------MainActivity------------ ");
        return super.onTouchEvent(event);
    }
}
