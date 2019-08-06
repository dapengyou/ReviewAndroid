package com.test.reviewandroid.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @createTime: 2019-08-05
 * @author: lady_zhou
 * @Description:
 */
public class Adapter extends FragmentPagerAdapter {
    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyFragment.newInstance(position);
            case 1:
                return MyFragment.newInstance(position);
            case 2:
                return MyFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
