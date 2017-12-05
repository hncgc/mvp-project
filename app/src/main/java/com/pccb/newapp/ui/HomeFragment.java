package com.pccb.newapp.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.R;
import com.pccb.newapp.base.BaseFragment;
import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.UserBase;
import com.pccb.newapp.global.ACache;
import com.pccb.newapp.global.PccbApplication;
import com.pccb.newapp.greendao.gen.UserBaseDao;
import com.pccb.newapp.mvp.home.IHomeView;
import com.pccb.newapp.net.http.HttpSubscriber;
import com.pccb.newapp.net.http.NewUrl;
import com.pccb.newapp.net.http.RetrofitHelper;
import com.pccb.newapp.net.http.exception.ApiException;
import com.pccb.newapp.net.http.util.HttpHelper;
import com.pccb.newapp.util.ConnectUtils;
import com.pccb.newapp.util.IDCardUtils;
import com.pccb.newapp.util.ToastUtil;
import com.pccb.newapp.view.slideview.AutoScrollViewPager;
import com.pccb.newapp.view.slideview.LoopViewPagerAdapter;
import com.pccb.newapp.view.slideview.ScaleTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // 图片轮播
    // 定义ViewPager对象
    @BindView(R.id.viewpager_home)
    AutoScrollViewPager viewpager_home;

    //首页横幅广告缓存
    public static final String KEY_BANNER_LIST_CACHE = "key_banner_list_cache";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //缓存
    private ACache mAcache;


    private OnFragmentInteractionListener mListener;

    List<BannerListBean.BannersEntity> mssbsBeans;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        viewpager_home.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewpager_home.startAutoScroll();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAcache = PccbApplication.getInstance().getACache();


        // 数据库测试
        UserBaseDao mUserDao = PccbApplication.getInstance().getDaoSession().getUserBaseDao();
        UserBase userBase = new UserBase(1001l,"13812341388");
        mUserDao.insertOrReplace(userBase);

        userBase = new UserBase(1002l,"15833331388");
        mUserDao.insertOrReplace(userBase);

        userBase = new UserBase(1003l,"15808885588");
        mUserDao.insertOrReplace(userBase);

        UserBase userBase1 = mUserDao.load(1001l);
        Logger.d("HomeFragment " + userBase1.getPhone_num());
        Logger.d("HomeFragment " + mUserDao.load(1002l).getPhone_num());

        List<UserBase> list = mUserDao.loadAll();
        for (UserBase user : list) {
            Logger.d("MHomeFragment onResume: " + user.getUser_id());
            Logger.d("HomeFragment onResume: " + user.getPhone_num());
        }

        getBennerInfo();
    }

    /**
     * 首页获取横幅广告
     */
    public void getBennerInfo() {
        // 检查网络连接状态
        if (!ConnectUtils.isAccessNetwork(mContext)) {
            ToastUtil.showToast(getActivity(), R.string.network_not_connected);
            return;
        }

        Map<String, String> mApiMap = new HashMap<>();
        mApiMap = new HashMap<>();
        mApiMap.put("service", NewUrl.BANNER_LIST);
        mApiMap.put("deviceId", IDCardUtils.getAndroidUUID());
        Logger.d("首页获取横幅广告 接口入参  service =" + NewUrl.BANNER_LIST + " deviceId = " + IDCardUtils.getAndroidUUID());
        RetrofitHelper
                .getService()
                .getRequestData(HttpHelper.getInstance().getCommomParams(mApiMap))
                .subscribeOn(Schedulers.io())
                //.doOnSubscribe(() -> showWaitDialog())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BannerListBean>bindHttpTransformer(BannerListBean.class))
                .subscribe(new HttpSubscriber<BannerListBean>() {
                    @Override
                    protected void error(ApiException e) {
                        //hideWaitDialog();
                        ToastUtil.showToast(mActivity, e.getMessage());
                        Logger.d("首页获取横幅广告 接口返回 error =" + e.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        //hideWaitDialog();
                    }

                    @Override
                    public void onNext(BannerListBean bannerListInof) {
                        Logger.d(bannerListInof.toString());
                        Logger.d("首页获取横幅广告 接口返回对象 =" + bannerListInof.toString());
                        if (bannerListInof.isSuccess()) {
                            //横幅广告缓存
                            mAcache.put(KEY_BANNER_LIST_CACHE, bannerListInof);
                            List<BannerListBean.BannersEntity> rows = bannerListInof.getBanners();
                            mssbsBeans = bannerListInof.getBanners();
                            loadingSlideShow();
                        }
                    }
                });
    }

    //加载轮播图
    public void loadingSlideShow() {
        // 实例化ViewPager适配器
        LoopViewPagerAdapter vpAdapter1 = new LoopViewPagerAdapter(mActivity, mssbsBeans);
        // 设置适配器数据
        viewpager_home.setAdapter(vpAdapter1);
        viewpager_home.setPageTransformer(false, new ScaleTransformer());

        // 设置监听 2017-01-18 hncgc
        viewpager_home.setOnPageChangeListener(new MyOnPageChangeListener());

        viewpager_home.setInterval(4000);
        viewpager_home.startAutoScroll();

    }

    /**
     * 轮播图指示器变换
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

}
