package com.pccb.newapp.view.slideview;
import android.content.Context;

/**
 * dip转px
 * @author cgc
 * @created 2017-08-10
 */
public class Utildip2px {
    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density);
    }

}
