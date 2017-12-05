package com.pccb.newapp.mvp.home;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.SystemStatisticsInfo;

/**
 * Created by cgc on 2017/11/23
 */

public class HomePresenter implements IHomePresenterView<IHomeView>, IHometPresenter {

    IHomeView mIHomeView;
    HomeModel mHomeMode;

    public HomePresenter(IHomeView mIHomeView) {
        this.mIHomeView = mIHomeView;
        this.mHomeMode = new HomeModel(this);
    }

    @Override
    public void attachView(IHomeView view) {
        this.mIHomeView = view;
    }

    @Override
    public void detachView() {
        this.mIHomeView = null;
    }


    @Override
    public void loadBannerSuccess(BannerListBean bannerListInof) {
         mIHomeView.onReturnBannerData(bannerListInof);
    }

    @Override
    public void loadStatisticsInfoSuccess(SystemStatisticsInfo contentListInfo) {
        mIHomeView.onReturnSystemStatisticsData(contentListInfo);
    }

    @Override
    public void setHeaderRefreshFinish() {
        mIHomeView.onHeaderRefreshFinish();
    }

    @Override
    public void setFooterLoadFinish() {
        mIHomeView.onFooterLoadFinish();
    }

    @Override
    public void setHideWaitDialog() {
        mIHomeView.onHideWaitDialog();
    }

    @Override
    public void setShowWaitDialog() {
        mIHomeView.onShowWaitDialog();

    }

    @Override
    public void setShowToast(String msg) {
        mIHomeView.onShowToast(msg);
    }

    @Override
    public Activity getActiviy() {
        return mIHomeView.returnActiviy();
    }

    @Override
    public Context getContext() {
        return mIHomeView.returnContext();
    }

    public void loadBaanerData() {
        mHomeMode.getBannerData();
    }

    public void loadStatisticsData(){
        mHomeMode.getStatisticsData();
    }

}
