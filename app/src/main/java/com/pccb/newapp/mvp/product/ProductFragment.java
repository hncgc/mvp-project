package com.pccb.newapp.mvp.product;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.R;
import com.pccb.newapp.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.lang.reflect.Field;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends BaseFragment {

    @BindView(R.id.tl_tab)
    TabLayout mTabTl;
    @BindView(R.id.vp_content)
    ViewPager mContentVp;

    private String[] mTitles = {"普资你我", "普资华企", "转让市场"};
    private String[] mTenderType = {ProductItemFragment.TENDER_TYPE_PERSONAL, ProductItemFragment.TENDER_TYPE_ENTERPRISE, ProductItemFragment.TENDER_TYPE_CLAIM};
    private ArrayList<Fragment> fragmentList;
    private TablayoutAdapter tablayoutAdapter;

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager();
        initTab();
    }

    private void initViewPager(){
        fragmentList = new ArrayList<Fragment>();
        for (int i = 0; i < mTitles.length; i++) {
            fragmentList.add(ProductItemFragment.newInstance(mTenderType[i]));
        }
        //FragmentManager fm = getSupportFragmentManager();
        //FragmentManager fm = getFragmentManager();       // 从其他Fragment返回时，传的参数在TextView中不显示
        FragmentManager fm = getChildFragmentManager();
        if (tablayoutAdapter == null) {
            tablayoutAdapter = new TablayoutAdapter(fm, fragmentList, mTitles);
        }
        mContentVp.setAdapter(tablayoutAdapter);
        Logger.d("ProductFragment initViewPager() ");
    }

    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabGravity(TabLayout.GRAVITY_FILL);
        //mTabTl.setTabTextColors(ContextCompat.getColor(mContext, R.color.gray), ContextCompat.getColor(mContext, R.color.colorPrimary));
        //mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        ViewCompat.setElevation(mTabTl, 10);

        mTabTl.setupWithViewPager(mContentVp);
        mTabTl.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(mTabTl,23,23);
            }
        });
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 设置TabLayout下方下划线的宽度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator (TabLayout tabs,int leftDip,int rightDip){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


}
