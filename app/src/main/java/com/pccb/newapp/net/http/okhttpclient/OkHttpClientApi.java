package com.pccb.newapp.net.http.okhttpclient;

import okhttp3.OkHttpClient;

/**
 * 当前类注释：
 * <p>
 * Author :LeonWang <p>
 * Created  2017/3/22.13:45 <p>
 * Description:API使用的{@link OkHttpClient}实例
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public class OkHttpClientApi {

    private volatile static OkHttpClientApi _okHttpClentApi;
    private OkHttpClient mOkHttpClient;

    private OkHttpClientApi(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    /**
     * 初始化
     *
     * @param okHttpClient {@link OkHttpClient}
     * @return OkHttpClientApi
     */
    public static OkHttpClientApi initClient(OkHttpClient okHttpClient) {
        if (_okHttpClentApi == null) {
            synchronized (OkHttpClientApi.class) {
                if (_okHttpClentApi == null) {
                    _okHttpClentApi = new OkHttpClientApi(okHttpClient);
                }
            }
        }
        return _okHttpClentApi;
    }

    public static OkHttpClientApi getInstance() {
        return initClient(null);
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }



}
