package com.pccb.newapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Preferences封装工具类
 *
 * @author cgc
 * @version 1.0
 * @created 2017-11-06
 */

public class PreferencesUtils {
    private static final String SP_NAME = "config";
    private static SharedPreferences spPreferences;
    private static final String USERCONFIG = "userconfig";

    /**
     * 获取应用是否首次启动
     *
     * @param context
     * @param versioncode
     */
    public static boolean isFirstLaunch(Context context, int versioncode) {
        boolean b = false;
        spPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        int thisvercode = spPreferences.getInt("LaunchVersionCode", 0);
        if (versioncode != thisvercode) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    /**
     * 设置应用首次启动
     *
     * @param context
     * @param versioncode
     */
    public static void setFirstLaunch(Context context, int versioncode) {
        spPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        spPreferences.edit().putInt("LaunchVersionCode", versioncode).commit();
        return;
    }

}
