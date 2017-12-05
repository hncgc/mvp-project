/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pccb.newapp.view.pulltorefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pccb.newapp.R;
import com.pccb.newapp.view.ProgressWheel;
import com.pccb.newapp.util.ViewUtil;


/**
 * 名称：PullRefreshHeaderView.java 
 * 描述：下拉刷新的Header View类.
 *
 * @author Zyl
 * @date：2015-07-12
 */
public class PullRefreshHeaderView extends LinearLayout {
	
	/** 上下文. */
	private Context mContext;
	
	/** 主View. */
	LinearLayout headerView;
	
	/** 箭头图标View. */
	ImageView arrowImageView;
	
	/** logo图标View. */
	ImageView logoImageView;
	
	/** 进度图标View. */
	private ProgressWheel headerProgressBar;
	
	/** 文本提示的View. */
	TextView tipsTextview;
	
	/** 当前状态. */
	private int mState = -1;

	/** 向上的动画. */
	private Animation mRotateUpAnim;
	
	/** 向下的动画. */
	private Animation mRotateDownAnim;
	
	/** 动画时间. */
	private final int ROTATE_ANIM_DURATION = 180;
	
	/** 显示 下拉刷新. */
	public final static int STATE_NORMAL = 0;
	
	/** 显示 松开刷新. */
	public final static int STATE_READY = 1;
	
	/** 显示 正在刷新.... */
	public final static int STATE_REFRESHING = 2;
	
	/** 显示刷新完成 */
	public final static int STATE_FINISH = 3;
	
	/**  Header的高度. */
	private int headerHeight;

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 */
	public PullRefreshHeaderView(Context context) {
		this(context,null);
	}

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public PullRefreshHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化View.
	 * 
	 * @param context the context
	 */
	private void initView(Context context) {
		mContext  = context;
		//顶部刷新栏整体内容
		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.VERTICAL);
		headerView.setGravity(Gravity.CENTER); 
		ViewUtil.setPadding(headerView, 0, 20, 10, 0);
		
		//显示箭头与进度
		FrameLayout headImage =  new FrameLayout(context);
		arrowImageView = new ImageView(context);
		arrowImageView.setImageResource(R.mipmap.default_ptr_flip);
		
		//显示logo
		logoImageView = new ImageView(context);
		logoImageView.setImageResource(R.mipmap.pull_down_logo_img);
		logoImageView.setScaleType(ScaleType.CENTER_INSIDE);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		headerProgressBar = (ProgressWheel) inflater.inflate(R.layout.loading_view, null).findViewById(R.id.loading_view_rw);
		headerProgressBar.setBarWidth(2);
		headerProgressBar.setVisibility(View.GONE);
		
		LayoutParams layoutParamsWW = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = ViewUtil.scale(mContext, 30);
		layoutParamsWW.height = ViewUtil.scale(mContext, 30);
		layoutParamsWW.rightMargin = ViewUtil.scale(mContext, 10);
		headImage.addView(arrowImageView,layoutParamsWW);
		headImage.addView(headerProgressBar,layoutParamsWW);

		//顶部刷新栏文本内容
		LinearLayout headTextLayout  = new LinearLayout(context);
		tipsTextview = new TextView(context);
		headTextLayout.setOrientation(LinearLayout.VERTICAL);
		headTextLayout.setGravity(Gravity.CENTER_VERTICAL);
		ViewUtil.setPadding(headTextLayout,0, 0, 0, 0);
		LayoutParams layoutParamsWW2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		headTextLayout.addView(tipsTextview,layoutParamsWW2);
		tipsTextview.setTextColor(getResources().getColor(R.color.common_text_large_color));
		ViewUtil.setTextSize(tipsTextview,24);

		LayoutParams layoutParamsWW3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.rightMargin = ViewUtil.scale(mContext, 10);

		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER);
		headerLayout.setPadding(0, 15, 0, 15);

		headerLayout.addView(headImage,layoutParamsWW3);
		headerLayout.addView(headTextLayout,layoutParamsWW3);


		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		LayoutParams logoImagelp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		logoImagelp.gravity = Gravity.CENTER;

		//添加大布局
		headerView.addView(logoImageView,logoImagelp);
		headerView.addView(headerLayout,lp);

		this.addView(headerView,lp);
		//获取View的高度
		ViewUtil.measureView(this);
		headerHeight = this.getMeasuredHeight();

		//动画对于箭头的动画上下
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);

		setState(STATE_NORMAL);
	}

	/**
	 * 设置状态.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.VISIBLE);
		}
		else if ( state == STATE_FINISH){
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.INVISIBLE);
			headerProgressBar.setVisibility(View.INVISIBLE);
		}
		else {
			arrowImageView.setVisibility(View.VISIBLE);
			headerProgressBar.setVisibility(View.INVISIBLE);
		}

		switch(state){
			case STATE_NORMAL:
				if (mState == STATE_READY) {
					arrowImageView.startAnimation(mRotateDownAnim);
				}
				if (mState == STATE_REFRESHING) {
					arrowImageView.clearAnimation();
				}
				tipsTextview.setText("下拉刷新");
				break;
			case STATE_READY:
				if (mState != STATE_READY) {
					arrowImageView.clearAnimation();
					arrowImageView.startAnimation(mRotateUpAnim);
					tipsTextview.setText("松开刷新");
				}
				break;
			case STATE_REFRESHING:
				tipsTextview.setText("正在刷新");
				break;
			case STATE_FINISH:
				tipsTextview.setText("刷新完毕");
				default:
			}
		mState = state;
	}

	/**
	 * 设置header可见的高度.
	 *
	 * @param height the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0) height = 0;
		LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * 获取header可见的高度.
	 *
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams)headerView.getLayoutParams();
		return lp.height;
	}

	/**
	 * 描述：获取HeaderView.
	 *
	 * @return the header view
	 */
	public LinearLayout getHeaderView() {
		return headerView;
	}
	
	/**
	 * 获取header的高度.
	 *
	 * @return 高度
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}
	
	/**
	 * 描述：设置背景颜色.
	 *
	 * @param color the new background color
	 */
	public void setBackgroundColor(int color){
		headerView.setBackgroundColor(color);
	}

	/**
	 * 描述：获取Header ProgressBar，用于设置自定义样式.
	 *
	 * @return the header progress bar
	 */
	public ProgressWheel getHeaderProgressBar() {
		return headerProgressBar;
	}

	/**
	 * 描述：得到当前状态.
	 *
	 * @return the state
	 */
    public int getState(){
        return mState;
    }

	/**
     * 设置提示状态文字的大小
     * @return
     */
	public void setStateTextSize(int size) {
		tipsTextview.setTextSize(size);
	}

}
