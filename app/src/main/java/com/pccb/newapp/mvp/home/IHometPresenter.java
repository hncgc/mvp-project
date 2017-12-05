package com.pccb.newapp.mvp.home;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.SystemStatisticsInfo;

/**
 * Created by cgc on 2017/7/12
 */

public interface IHometPresenter {
    void loadBannerSuccess(BannerListBean bannerListInof);
    void loadStatisticsInfoSuccess(SystemStatisticsInfo contentListInfo);
    void setHeaderRefreshFinish();
    void setFooterLoadFinish();
    void setHideWaitDialog();
    void setShowWaitDialog();
    void setShowToast(String msg);
    Activity getActiviy();
    Context getContext();
}

