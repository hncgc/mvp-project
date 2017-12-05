package com.pccb.newapp.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.view.View;

import com.pccb.newapp.R;
import com.pccb.newapp.global.PccbApplication;
import com.pccb.newapp.view.dialog.CommonDialog;

/**
 * Android通用工具方法
 *
 * @author cgc
 * <p>
 * public class CommonUtils {
 * <p>
 */
public class CommonUtils {

    /**
     * 获取系统版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.versionname_unknown);
        }
    }

    /**
     * 获取系统版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取线程堆栈信息
     */
    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    /**
     * 复制到剪切板
     *
     * @param string
     */
    @SuppressWarnings("deprecation")
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) PccbApplication.getInstance()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
    }

    /**
     * 退出应用
     */
    public static void showExitApp(final Context context) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(context.getResources().getString(
                R.string.you_are_sure_appexit));
        dialog.setRightButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PccbApplication.getInstance().AppExit(context, false);
            }
        });
        dialog.show();
    }
}
