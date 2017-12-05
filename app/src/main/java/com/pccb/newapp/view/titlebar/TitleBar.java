package com.pccb.newapp.view.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pccb.newapp.R;
import com.pccb.newapp.util.ScreenUtil;

/**
 * Created by WangYi
 *
 * @Date : 2017/11/3
 * @Desc : 通用TitleBar （左边一个导航按钮 中间标题 右边一个菜单按钮）
 */
public class TitleBar extends LinearLayout {
    private View mStatusBarView;
    private RelativeLayout mMainLayout;
    //标题
    private TextView mTitleView;
    //导航
    private ImageView mNavView;
    //菜单
    private ImageView mMenuView;
    //文本菜单
    private TextView mRightTextView;

    private String mTitle;
    private Drawable mNavIcon;
    private Drawable mMenuIcon;
    private String mRightText;
    private int mTitleColor;

    private OnClickListener mOnNavViewClickListener;
    private OnClickListener mOnMenuViewClickListener;

    //系统ActionBar高度
    private int mActionBarHeight;
    //系统默认点击效果
    private Drawable mSelectableBackground;

    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        mTitle = a.getString(R.styleable.TitleBar_title);
        mNavIcon = a.getDrawable(R.styleable.TitleBar_navIcon);
        mMenuIcon = a.getDrawable(R.styleable.TitleBar_menuIcon);
        mTitleColor = a.getColor(R.styleable.TitleBar_titleColor, getResources().getColor(R.color.textColorPrimary));
        mRightText = a.getString(R.styleable.TitleBar_rightText);
        int statusBarColor = a.getColor(R.styleable.TitleBar_statusBarColor, -1);
        a.recycle();

        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            mActionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        } else {
            mActionBarHeight = ScreenUtil.dip2px(48);
        }

        int attr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            attr = android.R.attr.selectableItemBackgroundBorderless;
        } else {
            attr = android.R.attr.selectableItemBackground;
        }

        if (context.getTheme().resolveAttribute(attr, tv, true)) {
            int[] attribute = new int[]{attr};
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(tv.resourceId, attribute);
            mSelectableBackground = typedArray.getDrawable(0);
        }

        setOrientation(VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mActionBarHeight);
        mMainLayout = new RelativeLayout(context);
        addView(mMainLayout, params);

        initNavView(context);
        initTitleView(context);

        //优先显示右边图标按钮
        if (mMenuIcon != null) {
            initMenuView(context);
        } else {
            initRightTextView(context);
        }

        if (statusBarColor != -1) {
            setupStatusBar(statusBarColor);
        }
    }

    /**
     * 添加左边导航按钮
     */
    private void initNavView(Context context) {
        if (mNavIcon == null) return;
        //mNavView = new ImageView(context);
        mNavView = new AppCompatImageView(context);
        mNavView.setScaleType(ImageView.ScaleType.CENTER);
        mNavView.setImageDrawable(mNavIcon);
        if (mSelectableBackground != null) {
            mNavView.setBackgroundDrawable(mSelectableBackground);
        }
        mNavView.setOnClickListener(v -> {
            if (mOnNavViewClickListener != null) {
                mOnNavViewClickListener.onClick(v);
                return;
            }

            if (v.getContext() instanceof Activity) {
                ((Activity) v.getContext()).onBackPressed();
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mActionBarHeight, mActionBarHeight);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        mMainLayout.addView(mNavView, params);
    }

    /**
     * 添加中间标题文字
     */
    private void initTitleView(Context context) {
        if (mTitle == null) return;

        mTitleView = new TextView(context);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setText(mTitle);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mMainLayout.addView(mTitleView, params);
    }

    /**
     * 添加右边菜单按钮
     */
    private void initMenuView(Context context) {
        if (mMenuIcon == null) return;

        mMenuView = new ImageView(context);
        mMenuView.setScaleType(ImageView.ScaleType.CENTER);
        mMenuView.setImageDrawable(mMenuIcon);
        if (mSelectableBackground != null) {
            mMenuView.setBackgroundDrawable(mSelectableBackground);
        }
        mMenuView.setOnClickListener(v -> {
            if (mOnMenuViewClickListener != null) {
                mOnMenuViewClickListener.onClick(v);
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mActionBarHeight, mActionBarHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        mMainLayout.addView(mMenuView, params);
    }

    /**
     * 添加右边文字按钮
     */
    private void initRightTextView(Context context) {
        if (mRightText == null) return;

        mRightTextView = new TextView(context);
        mRightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        mRightTextView.setGravity(Gravity.CENTER);
        mRightTextView.setText(mRightText);

        int padding = ScreenUtil.dip2px(16);
        mRightTextView.setPadding(padding, 0, padding, 0);
        mRightTextView.setBackground(mSelectableBackground);
        mRightTextView.setOnClickListener(v -> {
            if (mOnMenuViewClickListener != null) {
                mOnMenuViewClickListener.onClick(v);
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, mActionBarHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mMainLayout.addView(mRightTextView, params);
    }

    /**
     * 设置状态栏
     */
    public void setupStatusBar(@ColorInt int color) {
        if (mStatusBarView != null) {
            mStatusBarView.setBackgroundColor(color);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = ScreenUtil.getStatusBarHeight();
            mStatusBarView = new View(getContext());
            mStatusBarView.setBackgroundColor(color);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            addView(mStatusBarView, 0, params);
        }
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getString(resId));
    }

    public void setTitle(String title) {
        this.mTitle = title;
        if (mTitleView == null) {
            initTitleView(getContext());
        } else {
            mTitleView.setText(mTitle);
        }
    }

    public void setTitleColor(@ColorRes int resId) {
        this.mTitleColor = getResources().getColor(resId);
        if (mTitleView != null) {
            mTitleView.setTextColor(mTitleColor);
        }
    }

    public void setNavIcon(@DrawableRes int resId) {
        mNavIcon = getResources().getDrawable(resId);
        if (mNavView == null) {
            initNavView(getContext());
        } else {
            mNavView.setImageDrawable(mNavIcon);
            //mNavView.setImageResource(resId);
        }
    }

    public void setMenuIcon(@DrawableRes int resId) {
        mMenuIcon = getResources().getDrawable(resId);
        if (mMenuView == null) {
            initMenuView(getContext());
        } else {
            mMenuView.setImageDrawable(mMenuIcon);
        }
    }

    public void setRightText(String text) {
        mRightText = text;
        if (mRightTextView == null) {
            initRightTextView(getContext());
        } else {
            mRightTextView.setText(mRightText);
        }
    }

    public View getNavView() {
        return mNavView;
    }

    public void setOnNavViewClickListener(OnClickListener listener) {
        this.mOnNavViewClickListener = listener;
    }

    public void setOnMenuViewClickListener(OnClickListener listener) {
        this.mOnMenuViewClickListener = listener;
    }
}
