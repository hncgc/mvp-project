package com.pccb.newapp.ui.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.MainActivity;
import com.pccb.newapp.R;
import com.pccb.newapp.base.BaseActivity;
import com.pccb.newapp.bean.event.MainEvent;
import com.pccb.newapp.view.titlebar.BrownTitleBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Webview
 * @author cgc
 * @created 2017-11-22
 */
public class OpenWebActivity extends BaseActivity {
    public static final int RESULT_RETURN_OK = 0x8888;

    private static final int PCCB_PERMISSIONS_REQUEST_CALL_PHONE = 300;

    public static final String KEY_INTENT_TITLE = "key_intent_title";
    public static final String KEY_INTENT_URL = "key_intent_url";
    public static final String KEY_INTENT_TYPE = "key_intent_type"; //调用浏览器类型
    public static final String TAG_INTENT_TYPE_INTER = "tag_intent_type_inter"; //调用内部浏览器
    public static final String TAG_INTENT_TYPE_INTER_BACKBAR = "tag_intent_type_inter_backbar"; //调用内部浏览器显示backbar
    public static final String TAG_INTENT_TYPE_OUTER = "tag_intent_type_outer"; //调用外部浏览器
    public static final String TAG_INTENT_TYPE_UMENTOPEN = "tag_intent_type_umengopen"; //友盟推送打开内部web
    public static final String TAG_INTENT_TYPE_CLOSE_TITLE_BAR = "tag_intent_type_close_title_bar"; //关闭标题栏

    private String title_str;
    private String url_str;
    private String type_skip;

    @BindView(R.id.titleBar)
    BrownTitleBar mTitleBar;
    @BindView(R.id.progressBar)
    ProgressBar progress_pb;
    @BindView(R.id.webView)
    public WebView webView;


    private ArrayList<String> titles;
    private WebSettings mWebSettings;

    private Handler mHandler = new Handler();

    private String phoneNumber = "";

    static{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web);
        //ButterKnife.bind(OpenWebActivity.this);

        title_str = getIntent().getStringExtra(KEY_INTENT_TITLE);
        url_str = getIntent().getStringExtra(KEY_INTENT_URL);
        type_skip = getIntent().getStringExtra(KEY_INTENT_TYPE);

        Logger.i("url == " + url_str + " type_skip=" + type_skip);
        inintView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web;
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void inintView() {
        mTitleBar.setTitle(title_str);
        mTitleBar.setTitleColor(R.color.colorPrimaryDark);
        mTitleBar.setNavIcon(R.drawable.ic_arrow_back_black_24dp); //小米（4.4.4）闪退 华为Mate8正常
        mTitleBar.setOnNavViewClickListener(v -> {
            if (type_skip.equals(TAG_INTENT_TYPE_UMENTOPEN)) {
                //UIHelper.GotoMainActivity(OpenWebActivity.this);
            } else {
                //返回一级时切换到投资fragment
                EventBus.getDefault().post(new MainEvent(1));
                finish();
            }
        });


        if (type_skip.equals(TAG_INTENT_TYPE_INTER)) {
            // 内部控件访问
            webView.loadUrl(url_str);
        } else if (type_skip.equals(TAG_INTENT_TYPE_INTER_BACKBAR)) {
            // 内部控件访问
            webView.loadUrl(url_str);
            mTitleBar.setOnNavViewClickListener(v -> {
                //返回一级时切换到投资fragment
                EventBus.getDefault().post(new MainEvent(1));
                onBackPressed();
            });

            mTitleBar.setRightText("关闭");
            mTitleBar.setOnMenuViewClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回一级时切换到顾问fragment
                    MainActivity.mInitIndex = 2;
                    finish();
                }
            });
            titles = new ArrayList<>();
        } else if (type_skip.equals(TAG_INTENT_TYPE_OUTER)) {
            // 外部浏览器访问
            Uri uri = Uri.parse(url_str);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            finish();
        } else if (type_skip.equals(TAG_INTENT_TYPE_UMENTOPEN)) {
            // 内部控件访问
            webView.loadUrl(url_str);
        } else if (type_skip.equals(TAG_INTENT_TYPE_CLOSE_TITLE_BAR)) {
            // 内部控件访问
            webView.loadUrl(url_str);
            //关闭标题栏
            mTitleBar.setVisibility(View.GONE);
        }


        // 点击链接继续在当前browser中响应
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(final WebView view,
                                                    final String url) {
                Logger.i("url == " + url);
                if (url.startsWith("douyutvtest://")) { // 打开斗鱼直播APP
                    try {
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(url));
                        intent.setAction(Intent.ACTION_VIEW);
                        startActivity(intent);
                    } catch (Exception e) {
                        Logger.e("webview", e.getMessage(), e);
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            //在页面加载开始时调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            //页面加载结束时调用
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //uri为上页URL
                view.setVisibility(View.VISIBLE);
                Logger.i("url Finished == " + url);
            }

            //重写此方法才能够处理在浏览器中的按键事件
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);

            }

            //支持https
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }


        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progress_pb.setVisibility(View.GONE);
                } else {
                    if (progress_pb.getVisibility() == View.GONE)
                        progress_pb.setVisibility(View.VISIBLE);
                    progress_pb.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (type_skip.equals(TAG_INTENT_TYPE_INTER_BACKBAR)) {
                 	mTitleBar.setTitle(title);
                    titles.add(title);
                }
            }
        });
        // 设置支持JavaScript等
        mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setLightTouchEnabled(true);
        mWebSettings.setSupportZoom(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setDefaultTextEncodingName("UTF -8");
        webView.setHapticFeedbackEnabled(false);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        mWebSettings.setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //js处理
        webView.addJavascriptInterface(this, "jspccb");


    }


    // 点击系统“Back”键，整个Browser会调用finish()而结束自身
    // 浏览的网 页回退而不是退出浏览器，需要在当前Activity中处理并消费掉该Back事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            if (titles != null) {
                if ((titles.size() - 2) < 0) return true;
                String titleString = titles.get(titles.size() - 2);
                mTitleBar.setTitle(titleString);
                titles.remove(titles.size() - 1);
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (type_skip.equals(TAG_INTENT_TYPE_UMENTOPEN)) {
                //UIHelper.GotoMainActivity(OpenWebActivity.this);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


    /**
     * h5回调--返回手机号码
     *
     * @param mobileNumber;
     */
    private void returnMobileNumberCallBack(final String mobileNumber) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String backUrl = "javascript:appToH5ReturnPhone(" + "\'" + mobileNumber + "\'" + ")";
                webView.loadUrl(backUrl);
            }
        });
    }

    /**
     * H5调用--呼叫用户打电话
     *
     * @param phone 2016-11-01
     */
    @JavascriptInterface
    public void AppCallPhoneMethod(String phone) {
        phoneNumber = phone;
        if (!selfPermissionGranted(Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PCCB_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            callPhone(phoneNumber);
        }

    }

    /**
     * 打电话
     *
     * @param phone
     */
    public void callPhone(String phone) {
        Intent phoneIntent = new Intent("android.intent.action.CALL",
                Uri.parse("tel:" + phone));
        startActivity(phoneIntent);

    }

    /**
     * h5回调--返回用户ID
     *
     * @param userid;
     */
    private void returnUserIdCallBack(final String userid) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String backUrl = "javascript:appToH5ReturnUserid(" + "\'" + userid + "\'" + ")";
                webView.loadUrl(backUrl);
            }
        });
    }
    /**
     * h5回调--返回工具栏右边图片按钮ActionID
     *
     * @param actionId; 2016-11-10
     */
    private void returnActionIdCallBack(final int actionId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String backUrl = "javascript:appToH5ReturnActionid(" + "\'" + actionId + "\'" + ")";
                webView.loadUrl(backUrl);
            }
        });
    }


    /**
     * h5关闭窗口
     * 2017-01-17
     */
    @JavascriptInterface
    public void H5closeAppWindowMethod() {
        // 设置右边图像按钮
        finish();
    }



    /**
     * 动态切换全屏和非全屏模式
     *
     * @param isFulllScreen 2017-01-22
     */
    public void setFulllScreen(boolean isFulllScreen) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (isFulllScreen) {
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("requestCode: " + requestCode + " resultCode: " + resultCode);
        // 从通讯录选择一个手机号码
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                String phone = data.getStringExtra("phone");
                //PccbUtils.showAlertDialog(this, "我得到的手机号码" + phone);
                returnMobileNumberCallBack(phone);
            }
        }
        // h5跳转到登录并返回登录用户ID
        if (requestCode == 200) {
            String userid;
            if (resultCode == RESULT_RETURN_OK) {
                userid = data.getStringExtra("userid");
                //returnUserIdCallBack(userid);
                //返回DES加密后的用户ID 2016-11-22
                String en_user_id = userid;
                /* Encrypt */
                try {
                    //en_user_id = CryptUtils.encryptDES(en_user_id);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                returnUserIdCallBack(en_user_id);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除cookie
        clearWebViewCache();
        //删除此时之前的缓存
        clearCacheFolder(OpenWebActivity.this.getCacheDir(), System.currentTimeMillis());
    }


    // clear the cache before time numDays
    private int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

    // 清除cookie即可彻底清除缓存
    public void clearWebViewCache() {
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }

    /**
     * 权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PCCB_PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Logger.d("获取权限 CALL_PHONE : 获取到 ");
                    callPhone(phoneNumber);
                } else {
                    // 没有获取到权限，做特殊处理
                    Logger.d("获取权限 CALL_PHONE : 没有 ");
                }
                break;
            default:
                break;

        }

    }

    /**
     * 权限检查
     *
     * @param permission
     * @return
     */
    public boolean selfPermissionGranted(String permission) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int targetSdkVersion = 21;
            try {
                final PackageInfo info = this.getPackageManager().getPackageInfo(
                        this.getPackageName(), 0);
                targetSdkVersion = info.applicationInfo.targetSdkVersion;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Logger.i("PCCB targetSdkVersion ", targetSdkVersion + "");

            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = this.checkSelfPermission(permission)
                        == PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(this, permission)
                        == PermissionChecker.PERMISSION_GRANTED;
            }
        }
        Logger.i("PCCB 权限检查 result ", result == true ? "true" : "false");
        return result;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            if (titles != null) {
                String titleString = titles.get(titles.size() - 2);
                mTitleBar.setTitle(titleString);
                titles.remove(titles.size() - 1);
            }
        } else {
            super.onBackPressed();
        }
    }
}
