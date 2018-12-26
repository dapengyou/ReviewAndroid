package com.test.reviewandroid.bean;

import java.io.Serializable;

/**
 * @createTime: 2018/12/21
 * @author: lady_zhou
 * @Description: 秒表的bean
 */
public class ClockViewBean implements Serializable {
    private String id;
    private String time; //当前的时间记录
    private String differenceTime;//差值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDifferenceTime() {
        return differenceTime;
    }

    public void setDifferenceTime(String differenceTime) {
        this.differenceTime = differenceTime;
    }
}
