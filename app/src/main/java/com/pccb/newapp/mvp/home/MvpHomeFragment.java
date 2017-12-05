package com.pccb.newapp.mvp.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.R;
import com.pccb.newapp.base.BaseFragment;
import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.bean.SystemStatisticsInfo;
import com.pccb.newapp.bean.UserBase;
import com.pccb.newapp.global.ACache;
import com.pccb.newapp.global.PccbApplication;
import com.pccb.newapp.greendao.gen.UserBaseDao;
import com.pccb.newapp.util.ConnectUtils;
import com.pccb.newapp.util.StringUtils;
import com.pccb.newapp.util.ToastUtil;
import com.pccb.newapp.view.TextSwitcherView;
import com.pccb.newapp.view.pulltorefreshview.AbPullToRefreshView;
import com.pccb.newapp.view.pulltorefreshview.PullRefreshHeaderView;
import com.pccb.newapp.view.slideview.AutoScrollViewPager;
import com.pccb.newapp.view.slideview.LoopViewPagerAdapter;
import com.pccb.newapp.view.slideview.ScaleTransformer;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MvpHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MvpHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MvpHomeFragment extends BaseFragment implements IHomeView {

    //缓存Fragment view
    private View rootView;

    ZLoadingDialog dialog;

    HomePresenter mHomePresenter;

//    @BindView(R.id.swipe_refresh)
//    SuperSwipeRefreshLayout swipeRefreshLayout;

//    @BindView(R.id.ptr_pull)
//    AbPullToRefreshView ptr_pull;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    // 图片轮播
    // 定义ViewPager对象
    @BindView(R.id.viewpager_home)
    AutoScrollViewPager mViewPagerHome;

    @BindView(R.id.textSwitcherView)
    TextSwitcherView textSwitcherView;

    // 首页横幅广告缓存
    public static final String KEY_BANNER_LIST_CACHE = "key_banner_list_cache";
    // 统计信息缓存
    public static final String KEY_SYSTEM_STATISTICS_INFO_CACHE = "key_system_statistics_cache";

    //缓存
    private ACache mAcache;

    private OnFragmentInteractionListener mListener;

    List<BannerListBean.BannersEntity> mBannerList;

    SystemStatisticsInfo mSystemStatisticsInfo;

    LoopViewPagerAdapter mLoopViewPagerAdapter;

    private static boolean mIsFirst = true;

    public MvpHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MvpHomeFragment newInstance() {
        MvpHomeFragment fragment = new MvpHomeFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Logger.d("MvpHomeFragment onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("MvpHomeFragment onCreate");

        mHomePresenter = new HomePresenter(this);

        mAcache = PccbApplication.getInstance().getACache();
        // 数据库测试
        UserBaseDao mUserDao = PccbApplication.getInstance().getDaoSession().getUserBaseDao();
        UserBase userBase = new UserBase(1001l, "13812341388");
        mUserDao.insertOrReplace(userBase);

        userBase = new UserBase(1002l, "15833331388");
        mUserDao.insertOrReplace(userBase);

        userBase = new UserBase(1003l, "15808885588");
        mUserDao.insertOrReplace(userBase);

        UserBase userBase1 = mUserDao.load(1001l);
        Logger.d("MvpHomeFragment onCreate " + userBase1.getPhone_num());
        Logger.d("MvpHomeFragment onCreate " + mUserDao.load(1002l).getPhone_num());

        List<UserBase> list = mUserDao.loadAll();
        for (UserBase user : list) {
            Logger.d("MvpHomeFragment onCreate: " + user.getUser_id());
            Logger.d("MvpHomeFragment onCreate: " + user.getPhone_num());
        }

        getBennerInfo();
        getStatisticsData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d("MvpHomeFragment onCreateView");
        //return inflater.inflate(R.layout.fragment_home, container, false);

        // 切换Fragment时避免重复加载UI
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Logger.d("MvpHomeFragment onViewCreated");

        setRefreshLoadMore();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d("MvpHomeFragment onActivityCreated");
        // 将信息传到Activity
        onButtonPressed("MvpHomeFragment Message!");
    }

    /**
     * 设置下拉刷新、上拉加载更多
     */
    private void setRefreshLoadMore() {

        // 下拉刷新 AbPullToRefreshView
        /*
        ptr_pull.setPullRefreshEnable(true);
        ptr_pull.setLoadMoreEnable(true);
        ptr_pull.setOnHeaderRefreshListener(view -> {
            getBennerInfo();
            getStatisticsData();
        });

        //上拉显示底部提示
        ptr_pull.setOnFooterLoadListener(view -> {

            ptr_pull.onFooterLoadFinish();
        });
        */


        // 下拉刷新 SuperSwipeRefreshLayout
        /*
        //swipeRefreshLayout = (SuperSwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        // 设置下拉刷新头部背景色
        swipeRefreshLayout.setHeaderViewBackgroundColor(0xfffefefe);
        // 设置下拉时，被包含的View是否随手指的移动而移动 default true
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        // 开始刷新
                        getBennerInfo();
                        getStatisticsData();
                    }

                    @Override
                    public void onPullDistance(int distance) {
                        // 下拉距离
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        // 下拉过程中，下拉的距离是否足够出发刷新
                    }
                });


        // 当拉倒底部时，上拉加载更多
        swipeRefreshLayout
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //set false when finished
                        swipeRefreshLayout.setLoadMore(false);
                    }
                }, 5000);
            }

            @Override
            public void onPushEnable(boolean enable) {
                // 上拉过程中，上拉的距离是否足够出发刷新
            }

            @Override
            public void onPushDistance(int distance) {
                // 上拉距离
            }
        });
        */

        // 下拉刷新 SwipeRefreshLayout上拉下拉

        // 设置 Header 为 Material样式
        //refreshLayout.setRefreshHeader(new MaterialHeader(mContext).setShowBezierWave(true));
        //指定为经典Header，默认是 贝塞尔雷达Header
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshlayout.finishRefresh(2000);
                // 开始刷新
                getBennerInfo();
                getStatisticsData();
            }
        });

        // 上拉加载更多
        // 设置 Footer 为 球脉冲
        // refreshLayout.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        // 指定为经典Footer，默认是 BallPulseFooter
        refreshLayout.setRefreshFooter(new ClassicsFooter(mContext));

        // 内容不满屏幕的时候也开启加载更多
        refreshLayout.setEnableLoadmore(true);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });


    }

    /**
     * create Header View
     */
    private View createHeaderView() {
        // 创建下拉刷新头部的View样式
        PullRefreshHeaderView mHeaderView = new PullRefreshHeaderView(mContext);
        int mHeaderViewHeight = mHeaderView.getHeaderHeight();//113
        Logger.d("MvpHomeFragment mHeaderViewHeight = " + mHeaderViewHeight);
        mHeaderView.setGravity(Gravity.BOTTOM);
        mHeaderView.setPadding(0, -20, 0, 0);
        return mHeaderView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d("MvpHomeFragment onStart ");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String msg) {
        if (mListener != null) {
            // 将信息传到Activity
            mListener.onFragmentInteraction(msg);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewPagerHome.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewPagerHome.startAutoScroll();

        //if(!mIsFirst) {
        if (mBannerList != null) {
            // 加载轮播图
            showBaaner();
        }

        if (mSystemStatisticsInfo != null) {
            // 显示系统信息
            showSystemStatisticsIndo();
        }
        //} else {
        //    mIsFirst = false;
        //}
    }

    @Override
    public void onDestroy() {
        mHomePresenter.detachView();
        super.onDestroy();
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
    /**
     * 这个互动接口必须被含有Master_Fragment 的Activity继承
     * 来实现Fragment与Activity直接的互通
     * 更多信息请参考
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> .
     */

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String msg);
    }

    /**
     * 首页获取横幅广告
     */
    public void getBennerInfo() {
        // 检查网络连接状态
        if (!ConnectUtils.isAccessNetwork(mContext)) {
            ToastUtil.showToast(getActivity(), R.string.network_not_connected);
            loadBannerCacheData();
            //ptr_pull.onHeaderRefreshFinish();
        } else {
            mHomePresenter.loadBaanerData();
        }
    }

    /**
     * 加载轮播图缓存
     */
    private void loadBannerCacheData() {
        BannerListBean bannerListInof = (BannerListBean) mAcache.getAsObject(KEY_BANNER_LIST_CACHE);
        if (bannerListInof != null) {
            if (bannerListInof.isSuccess()) {
                mBannerList = bannerListInof.getBanners();
                // 加载轮播图
                if (mBannerList != null) {
                    //showBaaner();
                }
            }
        }
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


    @Override
    public void onShowWaitDialog() {
        dialog = new ZLoadingDialog(mContext);
        Z_TYPE type = Z_TYPE.ROTATE_CIRCLE;
        dialog.setLoadingBuilder(type)
                .setLoadingColor(Color.BLACK)
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .show();
    }

    @Override
    public void onHideWaitDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 从Model回调返回的Banner数据
     *
     * @param bannerListInof
     */
    @Override
    public void onReturnBannerData(BannerListBean bannerListInof) {
        if (bannerListInof.isSuccess()) {
            //横幅广告缓存
            mAcache.put(KEY_BANNER_LIST_CACHE, bannerListInof);

            mBannerList = bannerListInof.getBanners();
            // 加载轮播图
            if (mBannerList != null) {
                showBaaner();
            }
        }
    }


    // 显示轮播图
    public void showBaaner() {
        if (mBannerList == null) {
            return;
        }

        Logger.d("MvpHomeFragment showBaaner() mBannerList = " + mBannerList.toString());

        // 实例化ViewPager适配器
        mLoopViewPagerAdapter = new LoopViewPagerAdapter(mContext, mBannerList);

        Logger.d("MvpHomeFragment showBaaner() mLoopViewPagerAdapter = " + mLoopViewPagerAdapter.toString());

        // 设置适配器数据
        mViewPagerHome.setAdapter(mLoopViewPagerAdapter);

        mViewPagerHome.setPageTransformer(false, new ScaleTransformer());

        // 设置监听
        mViewPagerHome.setOnPageChangeListener(new MyOnPageChangeListener());

        mViewPagerHome.setInterval(4000);
        mViewPagerHome.startAutoScroll();
    }

    /**
     * 获取首页统计信息
     */
    public void getStatisticsData() {
        // 检查网络连接状态
        if (!ConnectUtils.isAccessNetwork(mContext)) {
            ToastUtil.showToast(getActivity(), R.string.network_not_connected);
            loadSystemStatisticsCacheData();
            //ptr_pull.onHeaderRefreshFinish();
        } else {
            mHomePresenter.loadStatisticsData();
        }
    }

    /**
     * 返回统计信息
     */
    @Override
    public void onReturnSystemStatisticsData(SystemStatisticsInfo listInfo) {
        // 系统统计缓存缓存
        mAcache.put(KEY_SYSTEM_STATISTICS_INFO_CACHE, listInfo);

        this.mSystemStatisticsInfo = listInfo;
        showSystemStatisticsIndo();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onHeaderRefreshFinish() {
        //ptr_pull.onHeaderRefreshFinish();
        //swipeRefreshLayout.setRefreshing(false);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onFooterLoadFinish() {
        //ptr_pull.onFooterLoadFinish();
        //swipeRefreshLayout.setLoadMore(false);
        refreshLayout.finishLoadmore();
    }

    /**
     * 加载系统统计缓存
     */
    private void loadSystemStatisticsCacheData() {
        mSystemStatisticsInfo = (SystemStatisticsInfo) mAcache.getAsObject(KEY_SYSTEM_STATISTICS_INFO_CACHE);
        if (mSystemStatisticsInfo != null) {
            if (mSystemStatisticsInfo.isSuccess()) {
                showSystemStatisticsIndo();
            }
        }
    }

    /**
     * 显示统计信息
     */
    void showSystemStatisticsIndo() {
        if (mSystemStatisticsInfo == null) return;
        Logger.d("MvpHomeFragment SystemStatisticsInfo = " + mSystemStatisticsInfo.toString());
        try {
            ArrayList<String> strings = new ArrayList<>();
            strings.add("数据截至：" + mSystemStatisticsInfo.getPuzicarInvestInfoDate());
            strings.add("成交笔数：" + mSystemStatisticsInfo.getPuzicarInvestNum());
            strings.add("成交金额：" + StringUtils.strTo2dotThousands(mSystemStatisticsInfo.getPuzicarTotalInvestMoney()) + "元");
            textSwitcherView.getResource(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onShowToast(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public Activity returnActiviy() {
        return mActivity;
    }

    @Override
    public Context returnContext() {
        return mContext;
    }

}
