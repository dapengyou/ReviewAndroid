package com.test.reviewandroid.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.reviewandroid.R;
import com.test.reviewandroid.bean.ClockViewBean;

import java.util.List;

/**
 * @createTime: 2018/12/21
 * @author: lady_zhou
 * @Description: 秒表的adapter
 */
public class ClockViewAdapter extends BaseQuickAdapter<ClockViewBean, BaseViewHolder> {
    public ClockViewAdapter(int layoutResId, @Nullable List<ClockViewBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClockViewBean clockViewBean) {
        helper.setText(R.id.tv_id, clockViewBean.getId())
                .setText(R.id.tv_diffenerce_time, clockViewBean.getDifferenceTime())
                .setText(R.id.tv_time, clockViewBean.getTime());
    }


}
