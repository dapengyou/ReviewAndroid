package com.test.reviewandroid.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private boolean isStart;//true，动画开始
    private boolean reStart;//true，再次开始动画
    private long mPauseTime;//暂停时的时间

    private ClockViewAdapter mAdapter;
    private int count = 0;//计数

    /*新旧分，秒，毫秒记录*/
    private int mMinute = 0;//新的分钟数
    private int mOldMinute = 0;//旧的分钟数
    private int mSecond = 0;//新的秒数
    private int mOldSecond = 0;//旧的秒数
    private int mMilliSecond = 0;//新的毫秒数
    private int mOldMilliSecond = 0;//旧的毫秒数
    private String mDifferenceTime;//前后秒的差值

    /*组成差值的分，秒，毫秒数值*/
    private int minute;
    private int second;
    private int milliSecond;

    private int mValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_view);
        ButterKnife.bind(this);
        initAdapter();
    }

    /**
     * @return : void
     * @date 创建时间: 2018/12/26
     * @author lady_zhou
     * @Description 初始化adapter
     */
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
                    //记录差值时间
                    mRvList.setVisibility(View.VISIBLE);
                    dealRecord(mClockView.recordTime());
                } else {
                    //清空数据，状态归为原始值
                    mRvList.setVisibility(View.GONE);

                    mClockView.clean();
                    mIvRestore.setVisibility(View.GONE);

                    /*adapter重置*/
                    initAdapter();
                    count = 0;
                    mOldMinute = 0;
                    mOldSecond = 0;
                    mOldMilliSecond = 0;
                }
                break;
            case R.id.iv_start:
                if (isStart) {
                    //暂停状态
                    mPauseTime = mClockView.pause(false);
                    mIvStart.setImageResource(R.mipmap.icon_start);
                    mIvRestore.setImageResource(R.mipmap.icon_restore);
                    isStart = false;
                    reStart = true;
                } else {
                    if (reStart) {
                        //暂停后再次开始
                        mClockView.restart(mPauseTime, true);
                    } else {
                        //开始
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

    /**
     * @param time :
     * @return : void
     * @date 创建时间: 2018/12/25
     * @author lady_zhou
     * @Description 处理记录时间
     */
    private void dealRecord(String time) {
        String id;

        /*设置id序号*/
        count++;
        if (count >= 10) {
            id = String.valueOf(count);
        } else {
            id = "0" + String.valueOf(count);
        }
        /*截取分，秒，毫秒*/
        mMinute = Integer.parseInt(time.substring(0, time.indexOf(":")));
        mSecond = Integer.parseInt(time.substring(3, time.indexOf(".")));
        mMilliSecond = Integer.parseInt(time.substring(time.indexOf(".") + 1));

        /*计算分，秒，毫秒相应的值*/
        if (mMinute - mOldMinute > 0) { //如从00：57：44 到  01：03.55
            if (mSecond - mOldSecond < 0) {
                minute = mMinute - mOldMinute - 1;
                if (mMilliSecond - mOldMilliSecond < 0) {
                    second = 60 + mSecond - mOldSecond - 1;
                    milliSecond = mValue + mMilliSecond - mOldMilliSecond;
                } else {
                    second = 60 + mSecond - mOldSecond;
                    milliSecond = mMilliSecond - mOldMilliSecond;
                }
            } else {
                minute = mMinute - mOldMinute;
                if (mMilliSecond - mOldMilliSecond < 0) {
                    second = mSecond - mOldSecond - 1;
                    milliSecond = mValue + mMilliSecond - mOldMilliSecond;
                } else {
                    second = mSecond - mOldSecond;
                    milliSecond = mMilliSecond - mOldMilliSecond;
                }
            }
        } else {
            minute = 0;
            if (mMilliSecond - mOldMilliSecond < 0) {
                second = mSecond - mOldSecond - 1;
                milliSecond = mValue + mMilliSecond - mOldMilliSecond;
            } else {
                second = mSecond - mOldSecond;
                milliSecond = mMilliSecond - mOldMilliSecond;
            }
        }
        /*记录老的分，秒，毫秒值*/
        mOldMinute = mMinute;
        mOldSecond = mSecond;
        mOldMilliSecond = mMilliSecond;

        /*设置分，秒，毫秒相应的显示的格式*/
        String minuteString;
        String secondString;
        String milliSecondString;
        if (milliSecond >= 10) {
            milliSecondString = String.valueOf(milliSecond);
        } else {
            milliSecondString = "0" + milliSecond;
        }

        if (second >= 10) {
            secondString = String.valueOf(second);
        } else {
            secondString = "0" + second;
        }

        if (minute >= 10) {
            minuteString = String.valueOf(minute);
        } else {
            minuteString = "0" + minute;
        }
        mDifferenceTime = "+" + minuteString + ":" + secondString + "." + milliSecondString;

        Log.d(TAG, "dealRecord: \t\t 分数差值：" + minute + "\t\t\t秒数差值：" + second + "\t\t\t毫秒数差值：" + milliSecond);


        /*设置bean  更新Adapter*/
        ClockViewBean clockViewBean = new ClockViewBean();
        clockViewBean.setId(id);
        clockViewBean.setTime(time);
        clockViewBean.setDifferenceTime(mDifferenceTime);
        mAdapter.addData(0, clockViewBean);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //停止动画
        mClockView.stopAnimator();
    }
}
