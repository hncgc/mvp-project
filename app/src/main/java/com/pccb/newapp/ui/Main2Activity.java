package com.pccb.newapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.pccb.newapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Main2Activity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    // 外部修改 Main2Activity.mInitIndex = index
    public static int mInitIndex;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    clickTabButton(0);
                    return true;
                case R.id.navigation_product:
                    clickTabButton(1);
                    return true;
                case R.id.navigation_consultant:
                    clickTabButton(2);
                    return true;
                case R.id.navigation_forum:
                    clickTabButton(3);
                    return true;
                case R.id.navigation_my:
                    clickTabButton(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        //mInitIndex = getIntent().getIntExtra("index", 1);
        initView();
    }

    private void initView() {
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //系统默认选中第一个,但是系统选中第一个不执行onNavigationItemSelected(MenuItem)方法,如果要求刚进入页面就执行clickTabOne()方法,则手动调用选中第一个
        mNavigation.setSelectedItemId(R.id.navigation_home);//根据具体情况调用

        mViewPager.addOnPageChangeListener(this);
        //为viewpager设置adapter
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickTabButton(mInitIndex);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        //ViewPager和BottomNaviationView联动,当ViewPager的某个页面被选中了,同时设置BottomNaviationView对应的tab按钮被选中
        switch (position) {
            case 0:
                mNavigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                mNavigation.setSelectedItemId(R.id.navigation_product);
                break;
            case 2:
                mNavigation.setSelectedItemId(R.id.navigation_consultant);
                break;
            case 3:
                mNavigation.setSelectedItemId(R.id.navigation_forum);
                break;
            case 4:
                mNavigation.setSelectedItemId(R.id.navigation_my);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private void clickTabButton(int index) {

        //为防止隔页切换时,滑过中间页面的问题,去除页面切换缓慢滑动的动画效果
        mViewPager.setCurrentItem(index, false);

        int resid = R.string.title_home;
        switch (index) {
            case 0:
                resid = R.string.title_home;
                break;
            case 1:
                resid = R.string.title_product;
                break;
            case 2:
                resid = R.string.title_consultant;
                break;
            case 3:
                resid = R.string.title_forum;
                break;
            case 4:
                resid = R.string.title_my;
                break;
            default:
                break;
        }

        //mTextMessage.setText(getResources().getText(resid));
        mTextMessage.setText(resid);
    }

    @OnClick(R.id.message)
    public void message() {
           clickTabButton(2);
           Log.i("Main2Activity", "clicked");
    }

}
