package com.pccb.newapp.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 尺寸计算相关工具类
 * Created by leonwang on 2017/3/23.
 */
public class ScreenUtil {

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        Resources res = Resources.getSystem();
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return res.getDimensionPixelSize(resId);
        }
        return dip2px(24);
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(float dipValue) {
        Resources res = Resources.getSystem();
        return (int) (dipValue * res.getDisplayMetrics().density);
    }
}
