package com.test.reviewandroid.recycleView.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * @createTime: 2019-07-03
 * @author: lady_zhou
 * @Description:
 */
public class CommodityBean implements Serializable, MultiItemEntity {
    private String name;
    private String logopath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogopath() {
        return logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    @Override
    public String toString() {
        return "CommodityBean{" +
                "name='" + name + '\'' +
                ", logopath='" + logopath + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
