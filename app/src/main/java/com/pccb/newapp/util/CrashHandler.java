package com.pccb.newapp.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pccb.newapp.global.PccbApplication;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @author
 *         UncaughtExceptionHandler： 线程未捕获异常控制器是用来处理未捕获异常的。
 *         如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框
 *         实现该接口并注册为程序中的默认未捕获异常处理
 *         这样当未捕获异常发生时，就可以做些异常处理操作
 *         例如：做出友好提示 或收集发送报告
 *         UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * Debug Log Tag
     */
    public static final String TAG = "CrashHandler";
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    PendingIntent restartIntent;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        Intent intent = new Intent();
        intent.setClassName("com.pccb.newapp", "com.pccb.app.StartActivity");
        restartIntent = PendingIntent.getActivity(mContext, 0,
                intent, Intent.FLAG_ACTIVITY_NEW_TASK);
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("pccb", ex.getMessage(), ex);
        // 本应用处理
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            restartApp();
        }
    }

    // 发生崩溃异常时,重启应用
    public void restartApp() {
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
        PccbApplication.getInstance().finishAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     *
     * @param ex
     * @return true:处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //LogUtils.e("MyException", ex);
        //显示异常信息&发送报告
        // 友盟错误统计
        //MobclickAgent.reportError(mContext, ex);
        return true;
    }


}