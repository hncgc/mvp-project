package com.pccb.newapp.mvp.home;

/**
 * Created by cgc on 2017/7/13
 */

public interface IHomePresenterView<V> {
    void attachView(V view);
    void detachView();
}
