package com.test.reviewandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClockViewActivity extends AppCompatActivity {
    private static final String TAG = "ClockViewActivity";
    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.iv_restore)
    ImageView mIvRestore;
    @BindView(R.id.iv_start)
    ImageView mIvStart;
    @BindView(R.id.clock_view)
    ClockView mClockView;
    @BindView(R.id.stopwatchview)
    StopWatchView mStopWatchView;
    private boolean isStart;
    private boolean reStart;
    private long mPauseTime;//暂停时的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_restore, R.id.iv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_restore:
                if (isStart) {
                } else {
                    mClockView.clean();
                    mIvRestore.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_start:
                if (isStart) {
                    mPauseTime = mClockView.pause(false);
                    mIvStart.setImageResource(R.mipmap.icon_start);
                    mIvRestore.setImageResource(R.mipmap.icon_restore);
                    isStart = false;
                    reStart = true;
                } else {
                    if (reStart) {
                        mClockView.restart(mPauseTime,true);
                    } else {
                        mClockView.start(true);
                    }

                    mIvStart.setImageResource(R.mipmap.icon_pause);
                    mIvRestore.setImageResource(R.mipmap.icon_count);
                    mIvRestore.setVisibility(View.VISIBLE);
                    isStart = true;
                    reStart = false;
                }
                break;
        }
    }
}
