package com.pccb.newapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast封装类
 */
public class ToastUtil {
    private static Toast sToast;

    /**
     * 显示Toast
     */
    public static void showToast(Context context, String msg, int duration) {
        if (sToast != null) {
            sToast.setText(msg);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(context, msg, duration);
        }
        sToast.show();
    }

    public static void showToast(Context context, int msgRes, int duration) {
        if (sToast != null) {
            sToast.setText(msgRes);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(context, msgRes, duration);
        }
        sToast.show();
    }

    public static void showToast(Context context, int msgRes) {
        if (sToast != null) {
            sToast.setText(msgRes);
            sToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            sToast = Toast.makeText(context, msgRes, Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    public static void showToast(Context context, String msgRes) {
        if (sToast != null) {
            sToast.setText(msgRes);
            sToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            sToast = Toast.makeText(context, msgRes, Toast.LENGTH_SHORT);
        }
        sToast.show();
    }

    /**
     * 关闭toast
     */
    public static void release() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}
