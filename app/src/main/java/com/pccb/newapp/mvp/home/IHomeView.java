package com.pccb.newapp.mvp.home;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.SystemStatisticsInfo;
import com.pccb.newapp.mvp.base.BaseView;

/**
 * Created by cgc on 2017/11/23
 */

public interface IHomeView {
    void onReturnBannerData(BannerListBean bannerListInof);
    void onReturnSystemStatisticsData(SystemStatisticsInfo contentListInfo);
    void onHeaderRefreshFinish();
    void onFooterLoadFinish();
    void onShowWaitDialog();
    void onHideWaitDialog();
    void onShowToast(String msg);
    Activity returnActiviy();
    Context returnContext();

}
