package com.test.reviewandroid.recycleView.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @createTime: 2019-07-04
 * @author: lady_zhou
 * @Description: 类别分类
 */
public class CategoryBean implements Serializable {
    private String name;
    private List<SubCatesBean> subCates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCatesBean> getSubCates() {
        return subCates;
    }

    public void setSubCates(List<SubCatesBean> subCates) {
        this.subCates = subCates;
    }
}
