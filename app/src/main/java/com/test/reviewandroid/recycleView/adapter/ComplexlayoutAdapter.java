package com.test.reviewandroid.recycleView.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.bean.FirstCommodityBean;
import com.test.reviewandroid.util.GlideUtils;

import java.util.List;

/**
 * Created by lady_zhou on 2018/4/9.
 */

public class ComplexlayoutAdapter extends BaseQuickAdapter<FirstCommodityBean, BaseViewHolder> {

    public ComplexlayoutAdapter(List<FirstCommodityBean> data) {
        super(R.layout.item_first_commodity, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FirstCommodityBean item) {
        GlideUtils.loadImage(mContext,  (ImageView) helper.getView(R.id.iv_image),item.getImageUrl());

        if (item.isHaveSex()) {
            //例  成人高端体检套餐（女）
            helper.setText(R.id.tv_title, item.getTitle() + "(" + item.getSex() + ")");
        } else {
            helper.setText(R.id.tv_title, item.getTitle());
        }
        //类型
        helper.setText(R.id.tv_type, item.getType());
        //金额
        helper.setText(R.id.tv_price, "￥" + item.getPrice() + "");
        //预约人数
        helper.setText(R.id.tv_people_number, item.getPeopleNumber() + "人已约");
    }
}
