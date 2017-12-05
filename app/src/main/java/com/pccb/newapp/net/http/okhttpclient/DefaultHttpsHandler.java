package com.pccb.newapp.net.http.okhttpclient;

import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 信任所有 https 的连接
 *
 * @author yanjun
 * @since 0.0.1
 */

public class DefaultHttpsHandler implements IHttpsHandler {

    private static DefaultHttpsHandler instance;

    public static DefaultHttpsHandler getInstance() {
        if (null == instance) {
            instance = new DefaultHttpsHandler();
        }
        return instance;
    }

    private TrustManager mTrustManager;
    private SSLSocketFactory mSSLSocketFactory;
    private HostnameVerifier mHostnameVerifier;

    @Override
    public TrustManager getTrustManager() throws Exception {
        if (null == mTrustManager) {
            mTrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws
                        CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            };
        }
        return mTrustManager;
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() throws Exception {
        if (null == mSSLSocketFactory) {
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{getTrustManager()}, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            mSSLSocketFactory = sslContext.getSocketFactory();
        }
        return mSSLSocketFactory;
    }

    @Override
    public HostnameVerifier getHostnameVerifier() throws Exception {
        if (null == mHostnameVerifier) {
            mHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    if (1 == 1) {
                        return true;
                    }
                    return false;
                }
            };
        }
        return mHostnameVerifier;
    }
}
