package com.pccb.newapp.net.http.transformers;

import android.text.TextUtils;

import com.pccb.newapp.net.http.HttpUtil;
import com.pccb.newapp.net.http.exception.ApiException;
import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;
import rx.functions.Func1;

/**
 * 当前类注释：http transformer转换器
 * Description:  返回BaseEntity的data泛型数据
 * 使用 new HttpTransformer<>(this.<T>bindUntilEvent(ActivityEvent.DESTROY));
 */

public class HttpTransFormer<T> implements Observable.Transformer<String, T> {

    private Class<?> mclazz;
    private final LifecycleTransformer<T> mTLifecycleTransformer;

    public HttpTransFormer(Class<?> clazz, LifecycleTransformer<T> lifecycleTransformer) {
        mclazz = clazz;
        mTLifecycleTransformer = lifecycleTransformer;
    }

    @Override
    public Observable<T> call(Observable<String> observable) {
        return observable
                .map(new Func1<String, T>() {
                    @Override
                    public T call(String response) {
                        if (TextUtils.isEmpty(response)) {
                            throw new ApiException("EXECUTE_OTHER_ERROR");
                        }
                        return HttpUtil.convert(response, mclazz);
                    }
                })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
                    @Override
                    public Observable<? extends T> call(Throwable throwable) {
                        throwable.printStackTrace();
                        return Observable.error(throwable);
                    }
                })
                .compose(mTLifecycleTransformer);
    }
}