package com.pccb.newapp.net.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.pccb.newapp.net.http.exception.ApiException;

/**
 * Created by YanJun on 2017/6/11.
 */

public class HttpUtil {

    public static <T> T convert(final String responseString, final Class<?> mclazz) {
        if (TextUtils.isEmpty(responseString)) {
            throw new ApiException("EXECUTE_OTHER_ERROR");
        }
        return (T) new Gson().fromJson(responseString, mclazz);
    }


}
