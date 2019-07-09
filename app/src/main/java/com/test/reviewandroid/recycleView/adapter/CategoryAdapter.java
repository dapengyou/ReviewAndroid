package com.test.reviewandroid.recycleView.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.bean.CategoryBean;
import com.test.reviewandroid.recycleView.bean.FirstCommodityBean;
import com.test.reviewandroid.util.GlideUtils;

import java.util.List;

/**
 * Created by lady_zhou on 2018/4/9.
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    private int checked;

    public void setChecked(int checked) {
        this.checked = checked;
        notifyDataSetChanged();
    }

    public CategoryAdapter(List<CategoryBean> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {

        if (helper.getAdapterPosition() == checked) {
            helper.setText(R.id.tv_name, item.getName());
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.text_orange));
            helper.setVisible(R.id.v_remark, true);
        } else {
            helper.setTextColor(R.id.tv_name, mContext.getResources().getColor(R.color.text_black));
            helper.setText(R.id.tv_name, item.getName());
            helper.setVisible(R.id.v_remark, false);


        }
    }
}
