package com.test.reviewandroid.recycleView.bean;

import java.io.Serializable;

/**
 * @createTime: 2019-07-03
 * @author: lady_zhou
 * @Description:
 */
public class LinkBannerBean implements Serializable {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "LinkBannerBean{" +
                "image='" + image + '\'' +
                '}';
    }
}
