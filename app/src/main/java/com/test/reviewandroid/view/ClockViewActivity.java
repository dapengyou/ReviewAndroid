package com.test.reviewandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.test.reviewandroid.R;
import com.test.reviewandroid.bean.ClockViewBean;
import com.test.reviewandroid.view.adapter.ClockViewAdapter;

import java.util.ArrayList;

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

    private boolean isStart;
    private boolean reStart;
    private long mPauseTime;//暂停时的时间

    private ClockViewAdapter mAdapter;
    private String time;
    private int count = 0;//计数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        ButterKnife.bind(this);
        initAdapter();
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvList.setLayoutManager(linearLayoutManager);
        mAdapter = new ClockViewAdapter(R.layout.item_clock_view, new ArrayList<ClockViewBean>());
        mRvList.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_restore, R.id.iv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_restore:
                if (isStart) {
                    mRvList.setVisibility(View.VISIBLE);
                    time = mClockView.recordTime();
                    dealRecord(time);
                } else {
                    mRvList.setVisibility(View.GONE);

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
                        mClockView.restart(mPauseTime, true);
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


    private void dealRecord(String time) {
        String id;
        count++;
        if (count >= 10) {
            id = String.valueOf(count);
        } else {
            id = "0" + String.valueOf(count);
        }
        ClockViewBean clockViewBean = new ClockViewBean();
        clockViewBean.setId(id);
        clockViewBean.setTime(time);
        mAdapter.addData(0,clockViewBean);
        mAdapter.notifyDataSetChanged();
    }
}
