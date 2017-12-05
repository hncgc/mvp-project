package com.pccb.newapp.base;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.net.http.transformers.HttpTransFormer;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * 应用程序Fragment的基类
 * @author cgc
 * @created 2015-04-24
 */
public class BaseFragment extends RxFragment {
    public Activity mActivity;
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    /**
     * retrofit配合rxjava生命周期统一处理
     */
    protected final <T> HttpTransFormer<T> bindHttpTransformer(Class<?> clazz) {
        return new HttpTransFormer<>(clazz, this.<T>bindUntilEvent(FragmentEvent.DESTROY));
    }
}
