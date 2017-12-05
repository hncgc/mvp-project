package com.pccb.newapp.net.http;

import android.support.annotation.NonNull;

import com.pccb.newapp.BuildConfig;
import com.pccb.newapp.net.http.PccbApi;
import com.pccb.newapp.net.http.okhttpclient.OkHttpClientApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 当前类注释：retrofit帮助类
 * <p>
 * Author :LeonWang <p>
 * Created  2017/3/22.13:58 <p>
 * Description:
 * <p>
 * E-mail:lijiawangjun@gmail.com
 */

public class RetrofitHelper {

    /**
     * 对外提供统一service
     *
     * @return
     */
    public static PccbApi getService() {
        String baseUrl = BuildConfig.SERVER_ADDRESS;
        return retrofit(baseUrl).create(PccbApi.class);
    }


    /**
     * 获取retrofit
     *
     * @param baseUrl host地址
     * @return Retrofit
     */
    @NonNull
    private static Retrofit retrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClientApi.getInstance().getOkHttpClient())
                .build();
    }
}
