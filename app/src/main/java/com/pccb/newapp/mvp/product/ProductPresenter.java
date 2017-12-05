package com.pccb.newapp.mvp.product;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.bean.TenderListResponse;

/**
 * Created by cgc on 2017/11/30
 */

public class ProductPresenter implements ProductContract.Presenter {
    ProductContract.View mProductView;
    ProductModel mProcuctmodel;

    public ProductPresenter(ProductContract.View productView) {
        this.mProductView = productView;
        this.mProductView.setPresenter(this);
        mProcuctmodel = new ProductModel(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTenderListSuccess(TenderListResponse response) {
        mProductView.onShowTenderList(response);
    }

    @Override
    public void setHeaderRefreshFinish() {
        mProductView.onHeaderRefreshFinish();
    }

    @Override
    public void setFooterLoadFinish() {
        mProductView.onFooterLoadFinish();
    }

    @Override
    public void setHideWaitDialog() {
        mProductView.onHideWaitDialog();
    }

    @Override
    public void setShowWaitDialog() {
        mProductView.onShowWaitDialog();
    }

    @Override
    public void setShowToast(String msg) {
        mProductView.onShowToast(msg);
    }

    @Override
    public Activity getActiviy() {
        return mProductView.returnActiviy();
    }

    @Override
    public Context getContext() {
        return mProductView.returnContext();
    }

    public void getTenderListData(String tenderType,  int curPage) {
        mProcuctmodel.getTenderList(tenderType, curPage);
    }
}
