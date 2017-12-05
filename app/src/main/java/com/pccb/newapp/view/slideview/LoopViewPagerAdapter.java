package com.pccb.newapp.view.slideview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pccb.newapp.R;
import com.pccb.newapp.bean.BannerListBean;
import com.pccb.newapp.imgloader.GlideImageLoader;
import com.pccb.newapp.ui.webview.OpenWebActivity;

import java.util.List;

/**
 * 功能描述：ViewPager适配器，用来绑定数据和view 循环
 * @author cgc
 * @created 2014-3-4
 */
public class LoopViewPagerAdapter extends PagerAdapter {

    private Context context;
    //private ArrayList<View> mViewList;

    private LayoutInflater inflater;

    List<BannerListBean.BannersEntity> mssbsBeans;
/*
    public LoopViewPagerAdapter(Context context, ArrayList<View> viewList) {
        this.context = context;
        this.mViewList = viewList;
    }
    */
    public LoopViewPagerAdapter(Context context, List<BannerListBean.BannersEntity> mssbsBeans) {
        this.context = context;
        this.mssbsBeans = mssbsBeans;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mssbsBeans.size() != 0) {
            // Infinite loop
            //return Integer.MAX_VALUE;
            return mssbsBeans.size();
        }
        return 0;
    }

    /**
     * 初始化position位置的界面
     */
        /*
		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPager) view).addView(mViewList.get(position % mViewList.size()), 0);
			return mViewList.get(position % mViewList.size());
		}
		*/
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int position1 = position;
        View view = inflater.inflate(R.layout.view_page_item, container, false);
        RoundAngleImageView iv = (RoundAngleImageView) view.findViewById(R.id.iv);
        //iv.setImageResource(mViewList.get(position));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        GlideImageLoader.displayImageView(context, mssbsBeans.get(position).getImage(), iv);
        //增加监听
        iv.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      // 跳转到浏览器
                                      if (mssbsBeans.get(position1).getLink().isEmpty() || mssbsBeans.get(position1).getLink().contains("null")) {

                                      } else {
                                          Intent intent = new Intent(context, OpenWebActivity.class);
                                          intent.putExtra(OpenWebActivity.KEY_INTENT_TITLE, "普资金服");
                                          intent.putExtra(OpenWebActivity.KEY_INTENT_URL, mssbsBeans.get(position1).getLink());
                                          intent.putExtra(OpenWebActivity.KEY_INTENT_TYPE, OpenWebActivity.TAG_INTENT_TYPE_INTER_BACKBAR);
                                          context.startActivity(intent);
                                      }


                                  }
                              }

        );


        container.addView(view);
        return view;
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        return (view == arg1);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}