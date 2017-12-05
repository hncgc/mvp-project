package com.pccb.newapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

/**
 * 网络工具类
 */
public class ConnectUtils {

    /**
     * 判断网络是否可访问
     * isAccessNetwork
     *
     * @param context
     * @return
     */
    public static boolean isAccessNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null
                && connManager.getActiveNetworkInfo().isAvailable()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是WIFI
     * isWifiWork
     *
     * @param context
     * @return
     */
    public static boolean isWifiWork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (state == State.CONNECTED || state == State.CONNECTING) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是cmwap
     *
     * @param context
     * @return
     */
    public static boolean isCMWap(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        String mobileInfo = mobInfo.getExtraInfo();
        if (mobileInfo != null && mobileInfo.equals("cmwap")) {
            return true;
        }
        return false;
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        String typename = null;
        if (networkInfo != null)
            typename = networkInfo.getTypeName();
        if (typename != null)
            Log.i("network net", "network type:" + typename);
        return typename;
    }
}