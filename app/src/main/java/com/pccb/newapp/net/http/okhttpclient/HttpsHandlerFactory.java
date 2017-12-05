package com.pccb.newapp.net.http.okhttpclient;

/**
 * https 处理工厂类
 *
 * @author yanjun
 * @since 0.0.1
 */

public class HttpsHandlerFactory {

    private static HttpsHandlerFactory instance;

    public static HttpsHandlerFactory getInstance() {
        if (null == instance) {
            instance = new HttpsHandlerFactory();
        }
        return instance;
    }

    private IHttpsHandler mHttpsHandler;

    public IHttpsHandler getHttpsHandler() {
        if (null == mHttpsHandler) {
            mHttpsHandler = new DefaultHttpsHandler();
        }
        return mHttpsHandler;
    }

    public void setHttpsHandler(IHttpsHandler httpsHandler) {
        mHttpsHandler = httpsHandler;
    }
}
