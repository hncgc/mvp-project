package com.pccb.newapp.mvp.product;

import android.app.Activity;
import android.content.Context;

import com.pccb.newapp.bean.TenderListResponse;
import com.pccb.newapp.mvp.base.BasePresenter;
import com.pccb.newapp.mvp.base.BaseView;

/**
 * Created by cgc on 2017/11/30
 */

public interface ProductContract {

    interface View extends BaseView<Presenter> {
        void onShowTenderList(TenderListResponse response);
        void onHeaderRefreshFinish();
        void onFooterLoadFinish();
        void onShowWaitDialog();
        void onHideWaitDialog();
        void onShowToast(String msg);
        Activity returnActiviy();
        Context returnContext();
    }

    interface Presenter extends BasePresenter {
        void loadTenderListSuccess(TenderListResponse response);
        void setHeaderRefreshFinish();
        void setFooterLoadFinish();
        void setHideWaitDialog();
        void setShowWaitDialog();
        void setShowToast(String msg);
        Activity getActiviy();
        Context getContext();
    }

    interface Model {
        void getTenderList(String TenderType, int curPage);
    }
}
