package com.test.reviewandroid.recycleView.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @createTime: 2019-07-10
 * @author: lady_zhou
 * @Description:
 */
public class ScreenBean implements Serializable {
    private List<SubCatesBean> mBeanList;

    public List<SubCatesBean> getBeanList() {
        return mBeanList;
    }

    public void setBeanList(List<SubCatesBean> beanList) {
        mBeanList = beanList;
    }
}
