package com.test.reviewandroid.recycleView.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.bean.CategoryBean;
import com.test.reviewandroid.recycleView.bean.CommodityBean;
import com.test.reviewandroid.recycleView.bean.SubCatesBean;
import com.test.reviewandroid.util.GlideUtils;

import java.util.List;

/**
 * Created by lady_zhou on 2018/4/9.
 */

public class SubCatesAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_SUBCATES = 1;

    public SubCatesAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_CATEGORY, R.layout.item_subcates);
        addItemType(TYPE_SUBCATES, R.layout.item_home_menu);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_CATEGORY:
                SubCatesBean subCatesBean = (SubCatesBean) item;
                helper.setText(R.id.tv_title, subCatesBean.getName());


                break;
            case TYPE_SUBCATES:
                CommodityBean commodityBean = (CommodityBean) item;
                helper.setText(R.id.tv_name, commodityBean.getName());

                GlideUtils.loadImage(mContext, (ImageView) helper.getView(R.id.iv_image), commodityBean.getLogopath());

                break;

        }
    }
}
