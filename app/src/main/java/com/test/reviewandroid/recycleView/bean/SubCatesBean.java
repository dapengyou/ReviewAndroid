package com.test.reviewandroid.recycleView.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @createTime: 2019-07-04
 * @author: lady_zhou
 * @Description: 下级分类
 */
public class SubCatesBean implements Serializable, MultiItemEntity {
    private String name;
    private List<CommodityBean> commodity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommodityBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        this.commodity = commodity;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
