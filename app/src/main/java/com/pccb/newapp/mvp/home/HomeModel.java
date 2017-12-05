package com.pccb.newapp.mvp.home;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.R;
import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.SystemStatisticsInfo;
import com.pccb.newapp.mvp.base.BaseModel;
import com.pccb.newapp.net.http.HttpSubscriber;
import com.pccb.newapp.net.http.NewUrl;
import com.pccb.newapp.net.http.RetrofitHelper;
import com.pccb.newapp.net.http.exception.ApiException;
import com.pccb.newapp.net.http.util.HttpHelper;
import com.pccb.newapp.util.ConnectUtils;
import com.pccb.newapp.util.IDCardUtils;
import com.pccb.newapp.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cgc on 2017/11/23
 */

public class HomeModel extends BaseModel implements IHomeModel{

    IHometPresenter mIHometPresenter;

    public HomeModel(IHometPresenter mIHometPresenter) {
        this.mIHometPresenter = mIHometPresenter;
    }

    /**
     * 首页获取横幅广告
     */
    @Override
    public void getBannerData() {
        // 检查网络连接状态
        if (!ConnectUtils.isAccessNetwork(mIHometPresenter.getContext())) {
            ToastUtil.showToast(mIHometPresenter.getActiviy(), R.string.network_not_connected);
            return;
        }

        Map<String, String> mApiMap =  new HashMap<>();
        mApiMap.put("service", NewUrl.BANNER_LIST);
        mApiMap.put("deviceId", IDCardUtils.getAndroidUUID());
        Logger.d("首页获取横幅广告 接口入参  service =" + NewUrl.BANNER_LIST + " deviceId = " + IDCardUtils.getAndroidUUID());
        RetrofitHelper
                .getService()
                .getRequestData(HttpHelper.getInstance().getCommomParams(mApiMap))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mIHometPresenter.setShowWaitDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BannerListBean>bindHttpTransformer(BannerListBean.class))
                .subscribe(new HttpSubscriber<BannerListBean>() {
                    @Override
                    protected void error(ApiException e) {
                        mIHometPresenter.setHeaderRefreshFinish();
                        mIHometPresenter.setHideWaitDialog();
                        //ToastUtil.showToast(mIHometPresenter.getActiviy(), e.getMessage());
                        mIHometPresenter.setShowToast(e.getMessage());
                        Logger.d("首页获取横幅广告 接口返回 error =" + e.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        mIHometPresenter.setHeaderRefreshFinish();
                        mIHometPresenter.setHideWaitDialog();
                    }

                    @Override
                    public void onNext(BannerListBean bannerListInof) {
                        Logger.d("首页获取横幅广告 接口返回对象 =" + bannerListInof.toString());
                        if (bannerListInof.isSuccess()) {
                            mIHometPresenter.loadBannerSuccess(bannerListInof);
                        }
                    }
                });

    }

    /**
     * 获取统计信息
     */
    @Override
    public void getStatisticsData() {
        Map<String, String> params = new HashMap<>();
        params.put("service", NewUrl.SYSTEM_STATISTICS_INFO);
        RetrofitHelper
                .getService()
                .getRequestData(HttpHelper.getInstance().getCommomParams(params))
                .subscribeOn(Schedulers.io())
                //.doOnSubscribe(() -> mIHometPresenter.setShowWaitDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<SystemStatisticsInfo>bindHttpTransformer(SystemStatisticsInfo.class))
                .subscribe(new HttpSubscriber<SystemStatisticsInfo>() {
                    @Override
                    protected void error(ApiException e) {
                        mIHometPresenter.setHeaderRefreshFinish();
                        mIHometPresenter.setHideWaitDialog();
                        mIHometPresenter.setShowToast(e.getMessage());
                        Logger.d("首页获取公共内容列表 接口返回 error =" + e.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        mIHometPresenter.setHeaderRefreshFinish();
                        mIHometPresenter.setHideWaitDialog();
                    }

                    @Override
                    public void onNext(SystemStatisticsInfo contentListInfo) {
                        if (null != contentListInfo && contentListInfo.isSuccess()) {
                            Logger.d(contentListInfo.toString());
                            mIHometPresenter.loadStatisticsInfoSuccess(contentListInfo);
                        }
                    }
                });


    }
}
