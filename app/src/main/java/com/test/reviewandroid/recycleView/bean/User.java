package com.test.reviewandroid.recycleView.bean;

import java.io.Serializable;

/**
 * @createTime: 2019-07-03
 * @author: lady_zhou
 * @Description:
 */
public class User implements Serializable {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
