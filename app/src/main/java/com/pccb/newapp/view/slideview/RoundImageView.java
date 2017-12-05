package com.pccb.newapp.view.slideview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 带外框的圆角image
 * @author cgc
 * @created 2017-08-30
 */
public class RoundImageView  extends android.support.v7.widget.AppCompatImageView {

    private int mBorderThickness = 4;
    private Context mContext;
    private int defaultColor = 0xFFFFFFFF;

    //总宽
    private float mTotalwidth;

    // 控件默认长、宽
    private int defaultWidth = 0;
    private int defaultHeight = 0;

    // 图像宽度
    float imgWidth;
    // 图像宽度
    float imgWHeight;


    public RoundImageView(Context context) {
        super(context);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        mTotalwidth = getResources().getDisplayMetrics().widthPixels;

        this.measure(0, 0);
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Config.ARGB_8888, true);
        defaultWidth = getWidth();
        defaultHeight = getHeight();
        int radius = 0;

        Log.d("RoundImageView", " defaultWidth: " + defaultWidth + "");

        Log.d("RoundImageView", " defaultHeight: " + defaultHeight + "");

//        radius = (defaultWidth < defaultHeight ? defaultWidth
//                : defaultHeight) / 2 - mBorderThickness;

        radius = 20;


        /*
        //剪裁成圆
        drawCircleBorder(canvas, radius,defaultColor );
        Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight
                / 2 - radius, null);
        */


        Bitmap roundCornerBitmap = getRoundCornerBitmap(bitmap, radius);
/*
        float x1 = Utildip2px.dip2px(mContext, 0) ;
        float x2 = mTotalwidth - Utildip2px.dip2px(mContext, 0) *2;
        float y1 = Utildip2px.dip2px(mContext, 0);
        float y2 = Utildip2px.dip2px(mContext, defaultHeight - 5) - Utildip2px.dip2px(mContext, 0) *2;
*/
        float x1 = 0 ;
        float x2 = defaultWidth;
        float y1 = 0;
        float y2 = defaultHeight;

        // 图像宽度
        imgWidth = defaultWidth;
        // 图像高度
        imgWHeight = defaultHeight;

        Log.d("RoundImageView", " imgWidth: " + imgWidth + "");
        Log.d("RoundImageView", " imgWHeight: " + imgWHeight + "");

        //RectF rectF = new RectF(x1- Utildip2px.dip2px(mContext, 2), 0, x1+ imgWidth + Utildip2px.dip2px(mContext, 2),y1 + imgWHeight + Utildip2px.dip2px(mContext, 2) );
        RectF rectF = new RectF(x1, y1, x2,y2);

        //画外框
        drawRoundRect(canvas, rectF, 20, 20);

        //画图像
        //canvas.drawBitmap(roundCornerBitmap, x1 , y1 , null);
        canvas.drawBitmap(roundCornerBitmap, x1 , y1 , null);
    }

    /**
     * 获取裁剪后的圆形图片
     *
     * @param radius 半径
     */
    public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        int squareWidth = 0, squareHeight = 0;
        int x = 0, y = 0;
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {// 高大于宽
            squareWidth = squareHeight = bmpWidth;
            x = 0;
            y = (bmpHeight - bmpWidth) / 2;
            // 截取正方形图片
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else if (bmpHeight < bmpWidth) {// 宽大于高
            squareWidth = squareHeight = bmpHeight;
            x = (bmpWidth - bmpHeight) / 2;
            y = 0;
            squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
                    squareHeight);
        } else {
            squareBitmap = bmp;
        }

        if (squareBitmap.getWidth() != diameter
                || squareBitmap.getHeight() != diameter) {
            squareBitmap = Bitmap.createScaledBitmap(squareBitmap, diameter,
                    diameter, true);

        }
        Bitmap output = Bitmap.createBitmap(squareBitmap.getWidth(),
                squareBitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, squareBitmap.getWidth(),
                squareBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(squareBitmap.getWidth() / 2,
                squareBitmap.getHeight() / 2, diameter / 2,
                paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(squareBitmap, rect, rect, paint);
        // bitmap回收(recycle导致在布局文件XML看不到效果)
        // bmp.recycle();
        // squareBitmap.recycle();
        // scaledSrcBmp.recycle();
        bmp = null;
        squareBitmap = null;
        scaledSrcBmp = null;
        return output;
    }

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color) {
        Paint paint = new Paint();
        /* 去锯齿 */
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        /* 设置paint的　style　为STROKE：空心 */
        paint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        paint.setStrokeWidth(mBorderThickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
    }


    /**
     * 把图片剪裁成圆角矩形
     * @param bitmap
     * @param pixels
     * @return
     */
    public Bitmap getRoundCornerBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 绘制圆角矩形
     * @param canvas
     * @param rect RectF对象
     * @param rx x方向上的圆角半径
     * @param ry y方向上的圆角半径
     */
    public void drawRoundRect(Canvas canvas, RectF rect, float rx, float ry) {
        final int color = 0xfff6f6f6;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawRoundRect(rect, rx, ry, paint);

    }
}
