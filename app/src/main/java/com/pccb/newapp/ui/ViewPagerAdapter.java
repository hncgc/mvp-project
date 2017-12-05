package com.pccb.newapp.ui;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pccb.newapp.mvp.home.MvpHomeFragment;
import com.pccb.newapp.mvp.product.ProductFragment;

/**
 * Created by cgc on 2017/11/1
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    //由于页面已经固定,故这里把Adapter需要的fragment提前创建
    private Fragment[] mFragments = new Fragment[]{
            //new HomeFragment(),
            new MvpHomeFragment(),
            new ProductFragment(),
            new ConsultantFragment(),
            new ForumFragment(),
            new MyFragment()
    };

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
