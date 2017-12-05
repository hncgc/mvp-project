package com.pccb.newapp.net.http;


import com.pccb.newapp.net.http.exception.ApiException;

import rx.Subscriber;

/**
 * HttpSubscriber
 * Created by Administrator on 2017/11/7 0007.
 */

public abstract class HttpSubscriber<T> extends Subscriber<T> {
    protected abstract void error(ApiException e);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            error((ApiException) e);
        } else {
            error(new ApiException("EXECUTE_OTHER_ERROR", e.getMessage()));
        }
    }

    @Override
    public void onNext(T t) {

    }
}
