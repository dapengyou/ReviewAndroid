package com.test.reviewandroid.recycleView.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @createTime: 2019-07-03
 * @author: lady_zhou
 * @Description:
 */
public class HeaderBean implements Serializable {
   private List<LinkBannerBean> linkBanner;
   private List<CommodityBean> commodity;

    public List<LinkBannerBean> getLinkBanner() {
        return linkBanner;
    }

    public void setLinkBanner(List<LinkBannerBean> linkBanner) {
        this.linkBanner = linkBanner;
    }

    public List<CommodityBean> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommodityBean> commodity) {
        this.commodity = commodity;
    }

    @Override
    public String toString() {
        return "HeaderBean{" +
                "linkBanner=" + linkBanner +
                ", commodity=" + commodity +
                '}';
    }
}
