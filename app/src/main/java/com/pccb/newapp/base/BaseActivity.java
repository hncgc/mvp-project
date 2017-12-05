package com.pccb.newapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.pccb.newapp.global.PccbApplication;

import butterknife.ButterKnife;

/**
 * 应用程序Activity的基类
 * @author cgc
 * @created 2017-11-06
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = BaseActivity.this.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(getLayoutResId());//把设置布局文件的操作交给继承的子类

        ButterKnife.bind(this);

        PccbApplication.getInstance().addActivity(this);

    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    abstract protected int getLayoutResId();

    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PccbApplication.getInstance().removeActivity(this);
    }
}
