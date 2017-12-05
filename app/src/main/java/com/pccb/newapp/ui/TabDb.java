package com.pccb.newapp.ui;

import com.pccb.newapp.R;
import com.pccb.newapp.mvp.home.MvpHomeFragment;
import com.pccb.newapp.mvp.product.ProductFragment;

public class TabDb {
    /***
     * 获得底部所有项
     */
    public static String[] getTabsTxt() {
        String[] tabs = {"首页", "产品", "顾问", "社区", "我的"};
        return tabs;
    }

    /***
     * 获得所有碎片
     */
    public static Class[] getFramgent() {
        Class[] cls = {MvpHomeFragment.class, ProductFragment.class, ConsultantFragment.class, ForumFragment.class, MyFragment.class};
        return cls;
    }

    /***
     * 获得所有点击前的图片
     */

    public static int[] getTabsImg() {
        int[] img = {R.mipmap.main_toolbar_home_normal, R.mipmap.main_toolbar_invest_normal, R.mipmap.main_toolbar_star_normal,
                R.mipmap.main_toolbar_forum_normal, R.mipmap.main_toolbar_account_normal};
        return img;
    }

    /***
     * 获得所有点击后的图片
     */
    public static int[] getTabsImgLight() {
        int[] img = {R.mipmap.main_toolbar_home_pressed, R.mipmap.main_toolbar_invest_pressed, R.mipmap.main_toolbar_star_pressed,
                R.mipmap.main_toolbar_forum_pressed, R.mipmap.main_toolbar_account_pressed};
        return img;
    }
}
