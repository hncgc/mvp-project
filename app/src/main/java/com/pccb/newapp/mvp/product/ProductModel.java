package com.pccb.newapp.mvp.product;

import com.pccb.newapp.bean.TenderListResponse;
import com.pccb.newapp.mvp.base.BaseModel;
import com.pccb.newapp.net.http.HttpSubscriber;
import com.pccb.newapp.net.http.NewUrl;
import com.pccb.newapp.net.http.RetrofitHelper;
import com.pccb.newapp.net.http.exception.ApiException;
import com.pccb.newapp.net.http.util.HttpHelper;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgc on 2017/11/30
 */

public class ProductModel extends BaseModel implements ProductContract.Model {
    ProductContract.Presenter mPresenter;

    public ProductModel(ProductContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }


    @Override
    public void getTenderList(String tenderType, int curPage) {
        Map<String, String> params = new HashMap<>();
        params.put("service", NewUrl.TENDER_LIST);
        params.put("start", String.valueOf(curPage));
        params.put("tenderTypeGroup", tenderType);
        params.put("limit", "10");
/*
        String userId = UserLogic.getInstance(getActivity()).getUserId();
        if (!TextUtils.isEmpty(userId)) {
            params.put("customerId", userId);
        }
*/
        RetrofitHelper.getService()
                .getRequestData(HttpHelper.getInstance().getCommomParams(params))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mPresenter.setShowWaitDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<TenderListResponse>bindHttpTransformer(TenderListResponse.class))
                .subscribe(new HttpSubscriber<TenderListResponse>() {
                    @Override
                    protected void error(ApiException e) {
                        mPresenter.setHideWaitDialog();
                        mPresenter.setHeaderRefreshFinish();
                        mPresenter.setFooterLoadFinish();
                        mPresenter.setShowToast(e.getMessage());
                    }

                    @Override
                    public void onNext(TenderListResponse response) {
                        mPresenter.setHideWaitDialog();
                        mPresenter.setHeaderRefreshFinish();
                        mPresenter.setFooterLoadFinish();
                        mPresenter.loadTenderListSuccess(response);
                    }
                });

    }
}
