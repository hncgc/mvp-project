package com.pccb.newapp.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;

import com.pccb.newapp.R;
import com.pccb.newapp.global.PccbApplication;
import com.pccb.newapp.view.dialog.CommonDialog;

/**
 * pccb公共工具类
 *
 * @author hncgc
 */
public class PccbUtils {

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
     * json异常处理
     */
    public static void showDialogForException(final Context mContext, String context, final Boolean isFinish) {
        CommonDialog cdg = new CommonDialog(mContext);
        cdg.setCancelable(false);
        cdg.setCanceledOnTouchOutside(false);
        cdg.setMessage(context);
        cdg.setRightButtonListener(sweetAlertDialog -> {
            if (isFinish) {
                ((Activity) mContext).finish();
            }
        });
        cdg.show();
    }

    /**
     * 退出应用
     */
    public static void showExitApp(Context context) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(context.getResources().getString(
                R.string.you_are_sure_appexit));
        dialog.setRightButtonListener(view -> {
            //PreferencesUtils.saveLockViewPwd(context, null);
            PccbApplication.getInstance().AppExit(context, false);
        });
        dialog.show();
    }

    /**
     * 显示对话框
     */
    public static void showAlertDialog(Context context, String msg) {
        CommonDialog cd = new CommonDialog(context);
        cd.setMessage(msg);
        cd.show();
    }

    /**
     * 显示对话框并finish activity
     */
    public static void showAlertDialogForReturn(final Context context, String msg) {
        CommonDialog cd = new CommonDialog(context);
        cd.setMessage(msg);
        cd.setRightButtonListener(dialog -> ((Activity) context).finish());
        cd.show();
    }

    /**
     * 提示登录
     */
    public static void showDialogLogin(Context context) {
        CommonDialog cd = new CommonDialog(context);
        cd.setMessage("您没有登录，请登录后查看！");
        cd.setLeftBtnText("关闭");
        cd.setRightBtnText("去登录");
        cd.setRightButtonListener(view -> {
            //UIHelper.GotoLoginActivity(context, UserLoginActivity.TAG_INTO_LOGIN_OTHER);
        });
        cd.show();
    }

    /**
     * 显示对话框并关闭当前Activity
     *
     * @param context
     * @param msg
     * @author 程恭纯
     */
    public static void showAlertDialogFinish(final Context context, String msg) {
        CommonDialog cd = new CommonDialog(context);
        cd.setMessage(msg);
        cd.setRightButtonListener(view -> ((Activity) context).finish());
        cd.show();
    }

    /**
     * 获取application应用<meta-data>元素
     *
     * @throws NameNotFoundException
     * @author hncgc
     */
    public static String getApplicationMeteData(Context context, String data_Name) throws NameNotFoundException {
        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
        return appInfo.metaData.getString(data_Name);
    }

    /**
     * 获取application应用<meta-data>元素中友盟渠道字符串
     *
     * @throws NameNotFoundException
     * @author hncgc
     */
    public static String getUmengChannel(Context context) {
        String umeng_channel = "";
        try {
            umeng_channel = PccbUtils.getApplicationMeteData(context, "UMENG_CHANNEL");
            //友盟渠道号 BD1001:百度手助 91ZS1001:91助手 AZ1001:安卓市场 YYB1001:应用宝 360SZ1001:360手机助手 XM1001:小米应用商场 QT1001:其他小市场
            if (umeng_channel.contains("BD1001")) {
                umeng_channel = "BD1001";
            }
            if (umeng_channel.contains("91ZS1001")) {
                umeng_channel = "91ZS1001";
            }
            if (umeng_channel.contains("AZ1001")) {
                umeng_channel = "AZ1001";
            }
            if (umeng_channel.contains("YYB1001")) {
                umeng_channel = "YYB1001";
            }
            if (umeng_channel.contains("360SZ1001")) {
                umeng_channel = "360SZ1001";
            }
            if (umeng_channel.contains("XM1001")) {
                umeng_channel = "XM1001";
            }
            if (umeng_channel.contains("QT1001")) {
                umeng_channel = "QT1001";
            }
            if (umeng_channel.contains("app1001")) {
                umeng_channel = "app1001";
            }
            if (umeng_channel.contains("PCCB")) {
                umeng_channel = "PCCB";
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return umeng_channel;
    }
}