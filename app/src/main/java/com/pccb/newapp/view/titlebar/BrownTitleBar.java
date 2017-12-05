package com.pccb.newapp.view.titlebar;

import android.content.Context;
import android.util.AttributeSet;

import com.pccb.newapp.R;

/**
 * Created by WangYi
 *
 * @Date : 2017/11/7
 * @Desc : 棕色TitleBar
 */
public class BrownTitleBar extends TitleBar {
    public BrownTitleBar(Context context) {
        super(context);
        initBar();
    }

    public BrownTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBar();
    }

    private void initBar() {
        int themeColor = getResources().getColor(R.color.colorPrimary);
        setBackgroundColor(themeColor);
        setupStatusBar(themeColor);
        setNavIcon(R.drawable.ic_arrow_back_black_24dp); //小米（4.4.4）闪退 华为Mate8正常
        //setNavIcon(R.mipmap.icon);
        setTitleColor(android.R.color.white);
    }
}
