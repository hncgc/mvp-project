package com.pccb.newapp.firstwelcome;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.pccb.newapp.MainActivity;
import com.pccb.newapp.R;
import com.pccb.newapp.global.PccbApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 引导页
 * @author cgc
 * @created 2017-11-02
 */
public class FirstShowViewPagerActivity extends Activity implements OnClickListener, OnPageChangeListener {

    ViewPager viewpage;
    Button button;

    private FirstShowViewPagerAdapter vpAdapter;
    private List<View> views;


    //引导图片资源
    private static final int[] pics = {R.mipmap.guide_pic_01,
            R.mipmap.guide_pic_02, R.mipmap.guide_pic_03};

    private List<Bitmap> bitmaps;

    //记录当前选中位置
    private int currentIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_first_show_view_pager);

        // 添加Activity到管理类
        PccbApplication.getInstance().addActivity(this);

        button = (Button) findViewById(R.id.button);
        views = new ArrayList<View>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        bitmaps = new ArrayList<Bitmap>();

        //初始化引导图片列
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setScaleType(ScaleType.CENTER_CROP);
            InputStream is = this.getResources().openRawResource(pics[i]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;

            bitmaps.add(BitmapFactory.decodeStream(is, null, options));
            iv.setImageBitmap(bitmaps.get(i));
            views.add(iv);
        }
        viewpage = (ViewPager) findViewById(R.id.viewpager);
        //初始化Adapter
        vpAdapter = new FirstShowViewPagerAdapter(views);
        viewpage.setAdapter(vpAdapter);
        //绑定回调
        viewpage.setOnPageChangeListener(this);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                for (int i = 0; i < bitmaps.size(); i++) {
                    if (bitmaps.get(i) != null && !bitmaps.get(i).isRecycled()) {
                        bitmaps.get(i).recycle();
                    }
                }
                Intent intent = new Intent(FirstShowViewPagerActivity.this, MainActivity.class);
                FirstShowViewPagerActivity.this.startActivity(intent);
                FirstShowViewPagerActivity.this.finish();
            }
        });

    }


    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        viewpage.setCurrentItem(position);
    }


    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        if (arg0 == pics.length - 1) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PccbApplication.getInstance().removeActivity(this);
    }
}
