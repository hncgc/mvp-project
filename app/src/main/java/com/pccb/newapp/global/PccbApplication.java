package com.pccb.newapp.global;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.pccb.newapp.BuildConfig;
import com.pccb.newapp.greendao.gen.DaoMaster;
import com.pccb.newapp.greendao.gen.DaoSession;
import com.pccb.newapp.net.http.LoggerInterceptor;
import com.pccb.newapp.net.http.okhttpclient.HttpsHandlerFactory;
import com.pccb.newapp.net.http.okhttpclient.IHttpsHandler;
import com.pccb.newapp.net.http.okhttpclient.OkHttpClientApi;
import com.pccb.newapp.util.CrashHandler;

import org.greenrobot.greendao.identityscope.IdentityScopeType;

import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static org.greenrobot.greendao.test.DbTest.DB_NAME;

/**
 * 全局配置类
 * @author cgc
 * @created 2017-11-02
 */
public class PccbApplication extends AppManager {
    private static final String TAG = "PccbApplication";

    private ACache mCache;
    private static Context sContext;
    private static PccbApplication instance;

    private DaoSession mDaoSession;

    private static Stack<Activity> activityStack;

    /**
     * 单一实例
     */
    public static PccbApplication getInstance() {
        if (null == instance) {
            instance = new PccbApplication();

            instance.activityStack = new Stack();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 异常处理初始化
        CrashHandler.getInstance().init(this);

        // 缓存初始化
        mCache = ACache.get(this, Constant.ACACHE_DIR_NAME);

        // 初始化数据库
        initGreenDao();

        // 日志初始化
        initLogger();

        // 添加okhttp3网络请求 @leonwang
        try {
            initRetrofit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sContext = getApplicationContext();
    }


    /**
     * 日志初始化
     */
    public void initLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
                .tag("PccbLogger")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

    }

    /**
     * 初始化数据库
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, DB_NAME, null);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession(IdentityScopeType.None);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public Context getContext() {
        return sContext;
    }


    public ACache getACache() {
        return mCache;
    }

    /**
     * 初始化Retrofit
     * <p>
     * 连接超时：10s<br>
     * 读取超时：10s<br>
     * 请求头信息  ----暂无
     * 日志输出{@link LoggerInterceptor}<br>
     * 请求缓存
     */
    private void initRetrofit() throws Exception {
        IHttpsHandler httpsHandler =
                HttpsHandlerFactory.getInstance().getHttpsHandler();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(httpsHandler.getSSLSocketFactory(), (X509TrustManager) httpsHandler.getTrustManager())
                .hostnameVerifier(httpsHandler.getHostnameVerifier())
                .addInterceptor(new LoggerInterceptor(LoggerInterceptor.Level.HEADERS))
                .cache(new Cache(new File(getCacheDir(), "http"), 10 * 1024 * 1024))
                .build();
        OkHttpClientApi.initClient(okHttpClient);
    }

}
