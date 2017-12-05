package com.pccb.newapp.net.http.okhttpclient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * https 处理
 *
 * @author yanjun
 * @since 0.0.1
 */

public interface IHttpsHandler {

    TrustManager getTrustManager() throws Exception;

    SSLSocketFactory getSSLSocketFactory() throws Exception;

    HostnameVerifier getHostnameVerifier() throws Exception;

}
