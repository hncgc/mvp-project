package com.pccb.newapp.mvp.base;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.pccb.newapp.net.http.transformers.HttpTransFormer;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.subjects.BehaviorSubject;

/**
 * Created by hncgc on 2017/7/14
 */

public class BaseModel {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    /**
     * retrofit配合rxjava生命周期统一处理
     * @param clazz
     * @param <T>
     * @return
     */
    protected final <T> HttpTransFormer<T> bindHttpTransformer(Class<?> clazz) {
        return new HttpTransFormer<>(clazz,this.<T>bindUntilEvent(FragmentEvent.DESTROY));
    }
}
