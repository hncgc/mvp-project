package com.pccb.newapp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.pccb.newapp.bean.event.MainEvent;
import com.pccb.newapp.mvp.home.MvpHomeFragment;
import com.pccb.newapp.ui.TabDb;
import com.pccb.newapp.util.CommonUtils;
import com.pccb.newapp.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.helper.PermissionHelper;

/**
 * 主控模块
 * @author cgc
 * @created 2017-11-06
 */
public class MainActivity extends FragmentActivity
        implements OnTabChangeListener,
        EasyPermissions.PermissionCallbacks,
        MvpHomeFragment.OnFragmentInteractionListener{

    @BindView(R.id.main_view)
    FrameLayout mMainView;
    @BindView(R.id.main_tab)
    FragmentTabHost mTabHost;

    private PermissionHelper mPermissionHelper;

    // 外部修改 Main2Activity.mInitIndex = index
    public static int mInitIndex = 0;

    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};

    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //StatusBarCompat.compat(this);
        //StatusBarCompat.compat(this, 0xFFFF0000);

        //初始化FragmentTabHost
        initHost();
        //初始化底部导航栏
        initTab();
        //默认选中
        mTabHost.onTabChanged(TabDb.getTabsTxt()[mInitIndex]);

    }

    /***
     * 初始化Host
     */
    private void initHost() {
        //调用setup方法 设置view
        mTabHost.setup(this, getSupportFragmentManager(),R.id.main_view);
        //去除分割线
        mTabHost.getTabWidget().setDividerDrawable(null);
        //监听事件
        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onFragmentInteraction(String msg) {
        // 接收MvpHomeFragment传送的信息
        //ToastUtil.showToast(this, msg);
        Logger.d("MainActivity onFragmentInteraction = " + msg);
    }

    /**
     * 初始化底部导航栏
     */
    private void initTab() {
        String[] tabs = TabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            //新建TabSpec
            TabSpec tabSpec = mTabHost.newTabSpec(TabDb.getTabsTxt()[i]);
            //设置view
            View view = LayoutInflater.from(this).inflate(R.layout.tab_foot, null);
            ((TextView) view.findViewById(R.id.foot_tv)).setText(TabDb.getTabsTxt()[i]);
            ((ImageView) view.findViewById(R.id.foot_iv)).setImageResource(TabDb.getTabsImg()[i]);
            tabSpec.setIndicator(view);
            //加入TabSpec
            mTabHost.addTab(tabSpec,TabDb.getFramgent()[i],null);
        }
    }

    @Override
    public void onTabChanged(String arg0) {
        //从分割线中获得多少个切换界面
        TabWidget tabw = mTabHost.getTabWidget();

        mInitIndex = mTabHost.getCurrentTab();
        Logger.d("MainActivity index = " + mInitIndex);

        for (int i = 0; i < tabw.getChildCount(); i++) {
            View v = tabw.getChildAt(i);
            TextView tv = (TextView) v.findViewById(R.id.foot_tv);
            ImageView iv = (ImageView) v.findViewById(R.id.foot_iv);
            //修改当前的界面按钮颜色图片
            if (i == mTabHost.getCurrentTab()) {
                tv.setTextColor(getResources().getColor(R.color.tab_light_color));
                iv.setImageResource(TabDb.getTabsImgLight()[i]);
            }else{
                tv.setTextColor(getResources().getColor(R.color.tab_color));
                iv.setImageResource(TabDb.getTabsImg()[i]);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickTabButton();
        Logger.d("MainActivity onResume index = " + mInitIndex);

    }

    @Override
    public void onStart() {
        super.onStart();
        //EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        //EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent event){
        mInitIndex = event.getIndex();
        Logger.d("MainActivity MainEvent index = " + mInitIndex);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    /**
     * 直接切换
     */
    private void clickTabButton() {
        mTabHost.setCurrentTab(mInitIndex);
    }

    @OnClick({R.id.main_view, R.id.main_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_view:
                break;
            case R.id.main_tab:
                cameraTask();
                locationAndContactsTask();

                break;
        }
    }

    /**
     * 退出应用监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            CommonUtils.showExitApp(MainActivity.this);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // Some permissions have been granted
        // ...
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // Some permissions have been denied
        // ...
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }
    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }

    private boolean hasSmsPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_SMS);
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasCameraPermission() ? yes : no,
                            hasLocationAndContactsPermissions() ? yes : no,
                            hasSmsPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();

        }
    }

}
