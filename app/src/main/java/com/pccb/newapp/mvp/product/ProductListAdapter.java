package com.pccb.newapp.mvp.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pccb.newapp.R;
import com.pccb.newapp.bean.Tender;
import com.pccb.newapp.util.ArithUtils;
import com.pccb.newapp.util.ConnectUtils;
import com.pccb.newapp.util.DateUtil;
import com.pccb.newapp.util.MoneyUtil;
import com.pccb.newapp.util.StringUtils;
import com.pccb.newapp.util.ToastUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投资列表适配器
 *
 * @author hncgc
 * @version 1.6
 * @created 2014-12-22
 * NewOne 2017-04-07
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewInvestList> {
    private List<Tender> list;
    private Context mContext;

    // private String info;
    ProductListAdapter(Context context, List<Tender> myList) {
        list = myList;
        this.mContext = context;
    }

    public List<Tender> getList() {
        return list;
    }

    @Override
    public ViewInvestList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item_view, parent, false);
        return new ViewInvestList(view);
    }

    @Override
    public void onBindViewHolder(ViewInvestList viewHolder, final int position) {
        Tender data = list.get(position);

        //续投标志
        if (data.isCanContinuedBuy()) {
            viewHolder.iv_can_continue.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_can_continue.setVisibility(View.GONE);
        }

        final int mbId = data.getId();
        String type = data.getType();

        //标的类型{limitTime:限时标,enterprise:华企通,eJiuding:E言九鼎}
        int iType = -1;
        if (type.equals("limitTime")) {
            iType = 0;
        } else if (type.equals("eJiuding")) {
            iType = 1;
        } else if (type.equals("enterprise")) {
            iType = 2;
        } else if ("noviciate".equals(type)) {
            iType = 3;
        } else if ("puzicar".equals(type)) {
            iType = 5;
        } else if ("puziNTB".equals(type)) {
            iType = 11;
        } else if ("RSS".equals(type) || "RSS_Enterprise".equals(type)) {
            iType = 12;
        }
        final int mbType = iType;

        //标的类型
        viewHolder.tv_img.setText(data.getTypeText());

        viewHolder.borrow_name.setText(data.getTitle());

        //产品类型
        String productType = data.getProductType();
        //还款类型 monthInterest: 每月付息到期还本 onePrincipal:一次性本息  avgInterest:等额本息
        String repaymentMode = data.getRepaymentMode();

        // 普资优选
        boolean isProductNewType = false;
        if ("new_standard_tender".equals(productType) || "new_loan_tender".equals(productType)) {
            if ("avgInterest".equals(repaymentMode)) {
                isProductNewType = true;
            }
        }

        if (isProductNewType) {
            if (mbType == 12) {
                viewHolder.iv_equal_principal_interest.setVisibility(View.GONE);
            } else {
                viewHolder.iv_equal_principal_interest.setVisibility(View.VISIBLE);
            }
            //viewHolder.tv_releaseTime.setVisibility(View.GONE);
            viewHolder.borrow_interest_rate_title.setText("预期年化净收益率(%)");

            //利率处理
            String minRate = data.getRateShowMinimum();
            String maxRate = data.getRateShowMaximum();
            viewHolder.borrow_interest_rate.setText(minRate + "~" + maxRate);
        } else {
            viewHolder.iv_equal_principal_interest.setVisibility(View.GONE);
            //viewHolder.tv_releaseTime.setVisibility(View.VISIBLE);
            viewHolder.borrow_interest_rate_title.setText("预期年化收益率(%)");

            //利率处理
            String rate = MoneyUtil.formatRate(String.valueOf(data.getRateShow()));
            viewHolder.borrow_interest_rate.setText(rate);
        }

        //发布时间 releaseTime
        String releaseTime = data.getRaiseStartTime();
        releaseTime = releaseTime.substring(0, releaseTime.length() - 6);
        viewHolder.tv_releaseTime.setText("发布时间：" + releaseTime + "点");

        //可投金额
        double leftAmount = ArithUtils.round(data.getLeftAmount(), 0);

        //标状态
        //标的状态{pendingReleased:等待发布,appointment:立即预约,buy:立即认购,fullScale:已满标,repaymenting:还款中,finish:已结束}
        String statusStr = data.getStatusStr();
        int statusInt = 0;
        if (statusStr.equals("pendingReleased")) {
            // 等待发布
            statusInt = 1;
        } else if (statusStr.equals("appointment")) {
            // 立即预约
            statusInt = 2;
        } else if (statusStr.equals("buy")) {
            // 立即认购
            statusInt = 3;
            if (leftAmount == 0.00) {
                statusInt = 4;
            }
        } else if (statusStr.equals("fullScale")) {
            // 满标
            statusInt = 4;
        } else if (statusStr.equals("repaymenting")) {
            // 还款中
            statusInt = 5;
        } else if (statusStr.equals("finish")) {
            // 已结束
            statusInt = 6;
        }

        //可投金额
        String leftAmountStr = "";
        if (leftAmount >= 10000) {
            leftAmount = ArithUtils.round(ArithUtils.div(leftAmount, 10000), 2);
            leftAmountStr = StringUtils.addStrThousands(leftAmount) + "万";
        } else {
            leftAmountStr = StringUtils.addStrThousands(leftAmount) + "";
        }

        //标的借款金额--项目总额
        double amount = ArithUtils.round(data.getAmount(), 0);
        String amountStr = "";
        if (amount >= 10000) {
            amount = ArithUtils.round(ArithUtils.div(amount, 10000), 2);
            amountStr = StringUtils.addStrThousands(amount) + "万";
        } else {
            amountStr = StringUtils.addStrThousands(amount) + "";
        }

        if (statusInt == 1 || statusInt == 3) {
            viewHolder.tv_borrow_need_title.setText("可投金额(元)");
            viewHolder.tv_borrow_need.setText(leftAmountStr);
        } else {
            viewHolder.tv_borrow_need_title.setText("项目总额(元)");
            viewHolder.tv_borrow_need.setText(amountStr);
        }

        if (statusInt == 1) {
            // 等待发布
            viewHolder.b_status.setText("等待发布");
            viewHolder.b_status
                    .setBackgroundResource(R.drawable.box_gradient_red_yellow);
            viewHolder.b_status.setTextColor(android.graphics.Color
                    .parseColor("#ffffff"));
            viewHolder.b_status.setVisibility(View.VISIBLE);
            viewHolder.iv_is_over.setVisibility(View.GONE);
            viewHolder.fl_progress.setVisibility(View.GONE);
        } else if (statusInt == 2) {
            // 立即预约
            viewHolder.b_status.setText("立即预约");
            viewHolder.b_status
                    .setBackgroundResource(R.drawable.bg_border_yellow_style2);

            viewHolder.b_status.setTextColor(android.graphics.Color
                    .parseColor("#FFBE00"));
            viewHolder.b_status.setVisibility(View.VISIBLE);
            viewHolder.iv_is_over.setVisibility(View.GONE);
            viewHolder.fl_progress.setVisibility(View.GONE);

        } else if (statusInt == 3) {
            // 立即投资
            viewHolder.b_status.setText("确认出借");
            viewHolder.b_status
                    .setBackgroundResource(R.drawable.box_gradient_red_yellow);
            viewHolder.b_status.setTextColor(android.graphics.Color
                    .parseColor("#333333"));

            viewHolder.iv_is_over.setVisibility(View.GONE);
            viewHolder.b_status.setVisibility(View.GONE);
            viewHolder.fl_progress.setVisibility(View.VISIBLE);
        } else if (statusInt == 4) {
            // 满标
            viewHolder.b_status.setText("已满标");
            viewHolder.b_status
                    .setBackgroundResource(R.drawable.btn_gray_style01);
            viewHolder.b_status.setTextColor(android.graphics.Color
                    .parseColor("#cccccc"));
            viewHolder.b_status.setVisibility(View.VISIBLE);
            viewHolder.iv_is_over.setVisibility(View.GONE);
            viewHolder.fl_progress.setVisibility(View.GONE);

        } else if (statusInt == 5) {
            // 还款中
            viewHolder.b_status.setText("还款中");
            viewHolder.b_status
                    .setBackgroundResource(R.drawable.bg_border_gray_light_style_1);
            viewHolder.b_status.setTextColor(android.graphics.Color
                    .parseColor("#9d9d9d"));
            viewHolder.b_status.setVisibility(View.VISIBLE);
            viewHolder.iv_is_over.setVisibility(View.GONE);
            viewHolder.fl_progress.setVisibility(View.GONE);

        } else if (statusInt == 6) {
            // finish
            viewHolder.b_status.setText("已结束");
            viewHolder.b_status.setVisibility(View.GONE);
            viewHolder.iv_is_over.setVisibility(View.VISIBLE);
            viewHolder.fl_progress.setVisibility(View.GONE);
        }

        if (statusInt > 3) {
            viewHolder.borrow_interest_rate.setTextColor(mContext.getResources().getColor(R.color.account_gray));
            viewHolder.tv_bonus_rate_show.setTextColor(mContext.getResources().getColor(R.color.account_gray));
            viewHolder.borrow_duration.setTextColor(mContext.getResources().getColor(R.color.account_gray));
        } else {
            viewHolder.borrow_interest_rate.setTextColor(mContext.getResources().getColor(R.color.text_color_red_02));
            viewHolder.tv_bonus_rate_show.setTextColor(mContext.getResources().getColor(R.color.text_color_red_02));
            viewHolder.borrow_duration.setTextColor(mContext.getResources().getColor(R.color.text_color_red_02));
        }

        //倒计时
        StartCountTimer startTimeCount = (StartCountTimer) viewHolder.tv_releaseTime.getTag();
        if (null != startTimeCount) {
            startTimeCount.cancel();
            viewHolder.tv_releaseTime.setTag(null);
        }
        if (statusInt == 2) {
            int start_time_int = Integer.parseInt(DateUtil.date2TimeStamp(data.getRaiseStartTime(), "yyyy-MM-dd HH:mm:ss"));
            int currentTime = Integer.parseInt(DateUtil.date2TimeStamp(data.getCurrServerTime(), "yyyy-MM-dd HH:mm:ss"));
            long restTime = start_time_int - currentTime;

            startTimeCount = new StartCountTimer(restTime * 1000, 1000, viewHolder.tv_releaseTime, releaseTime);
            viewHolder.tv_releaseTime.setTag(startTimeCount);
            startTimeCount.start();
        }

        //奖励抵用券比率展示
        if (data.getBonusRateShow().isEmpty()) {
            viewHolder.tv_bonus_rate_show.setVisibility(View.GONE);
        } else {
            viewHolder.tv_bonus_rate_show.setText(data.getBonusRateShow());
            viewHolder.tv_bonus_rate_show.setVisibility(View.VISIBLE);
        }

        if (mbType == 12) {
            viewHolder.tv_period_title.setText("封闭期");
        } else {
            viewHolder.tv_period_title.setText("锁定期");
        }

        //投资期限
        String periodStr;
        int period = data.getPeriod();
        String periodType = data.getPeriodType();
        if (periodType.equals("month")) {
            periodStr = "个月";
        } else {
            periodStr = "天";
        }
        viewHolder.borrow_duration.setText(String.valueOf(period));
        viewHolder.borrow_duration_unit.setText(periodStr);

        //起投金额
        //viewHolder.per_transfer.setText(StringUtils.addStrThousands(data.getStartAmount()));

        //进度
        String percentStr = MoneyUtil.formatRate(String.valueOf(data.getPercent()));
        int percent = new BigDecimal(MoneyUtil.mul(percentStr, "100")).intValue();
        if (percent >= 5000) {
            viewHolder.progress_text.setTextColor((android.graphics.Color.parseColor("#ffffff")));
        } else {
            viewHolder.progress_text.setTextColor((android.graphics.Color.parseColor("#666666")));
        }
        viewHolder.progress_text.setText(percentStr + "%");
        viewHolder.progress.setProgress(percent);


        // 列表(即将发布、立即认购、交易成功、已经结束)按钮
        viewHolder.b_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectUtils.isAccessNetwork(mContext)) {
                    ToastUtil.showToast(mContext, R.string.network_not_connected);
                    return;
                }
                /*
                Intent intent = new Intent(mContext, InvestDetailTabActivity.class);
                intent.putExtra("isStar", false);
                intent.putExtra("star_id", 0);
                intent.putExtra("mbId", mbId);
                intent.putExtra("mbType", mbType);
                ((Activity) mContext).startActivityForResult(intent, 0);
                */
            }
        });
        // 2015-01-22
        // ListView.setOnItemClickListener 点击无效, 在内部LinearLayout加监听器
        viewHolder.ll_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ConnectUtils.isAccessNetwork(mContext)) {
                    ToastUtil.showToast(mContext, R.string.network_not_connected);
                    return;
                }
                /*
                Intent intent = new Intent(mContext, InvestDetailTabActivity.class);
                intent.putExtra("isStar", false);
                intent.putExtra("star_id", 0);
                intent.putExtra("mbId", mbId);
                intent.putExtra("mbType", mbType);
                ((Activity) mContext).startActivityForResult(intent, 0);
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewInvestList extends RecyclerView.ViewHolder {
        public LinearLayout ll_list;
        // 等额本息
        private ImageView iv_equal_principal_interest;
        //续投标志
        public ImageView iv_can_continue;

        public TextView tv_img;

        public TextView borrow_name;

        //发布时间
        public TextView tv_releaseTime;

        public Button b_status;

        public ImageView iv_is_over;

        public TextView borrow_duration;
        public TextView borrow_duration_unit;

        public TextView borrow_interest_rate_title;
        //public TextView per_transfer;
        public TextView borrow_interest_rate;

        //奖励抵用券比率展示
        public TextView tv_bonus_rate_show;

        public FrameLayout fl_progress;
        public ProgressBar progress;
        public TextView progress_text;

        //可股金额标题
        public TextView tv_borrow_need_title;
        //可投金额
        public TextView tv_borrow_need;
        TextView tv_period_title;

        public ViewInvestList(View v) {
            super(v);
            tv_period_title = (TextView) v.findViewById(R.id.tv_period_title);
            // 等额本息
            iv_equal_principal_interest = (ImageView) v.findViewById(R.id.iv_equal_principal_interest);
            //续投标志
            iv_can_continue = (ImageView) v.findViewById(R.id.iv_can_continue);

            ll_list = (LinearLayout) v.findViewById(R.id.ll_list);
            tv_img = (TextView) v.findViewById(R.id.tv_img);

            borrow_name = (TextView) v.findViewById(R.id.borrow_name);

            tv_releaseTime = (TextView) v.findViewById(R.id.tv_releaseTime);

            b_status = (Button) v.findViewById(R.id.btn_b_status);
            iv_is_over = (ImageView) v.findViewById(R.id.iv_is_over);

            borrow_duration = (TextView) v.findViewById(R.id.borrow_duration);

            borrow_duration_unit = (TextView) v.findViewById(R.id.borrow_duration_unit);

            //per_transfer = (TextView) v.findViewById(R.id.per_transfer);

            borrow_interest_rate_title = (TextView) v.findViewById(R.id.borrow_interest_rate_title);
            borrow_interest_rate = (TextView) v.findViewById(R.id.borrow_interest_rate);

            tv_bonus_rate_show = (TextView) v.findViewById(R.id.tv_bonus_rate_show);

            fl_progress = (FrameLayout) v.findViewById(R.id.fl_progress);
            progress = (ProgressBar) v.findViewById(R.id.progress);
            progress_text = (TextView) v.findViewById(R.id.progress_text);
            tv_borrow_need_title = (TextView) v.findViewById(R.id.tv_borrow_need_title);
            tv_borrow_need = (TextView) v.findViewById(R.id.tv_borrow_need);

        }
    }

    /**
     * 倒计时
     */
    class StartCountTimer extends CountDownTimer {
        private TextView textView;
        private String releaseTime;

        public StartCountTimer(long millisInFuture, long countDownInterval, TextView textView, String releaseTime) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
            this.textView = textView;
            this.releaseTime = releaseTime;
        }

        // 计时完毕时触发
        @Override
        public void onFinish() {
            //发布时间
            textView.setText("发布时间：" + releaseTime);
        }

        // 计时过程显示
        @Override
        public void onTick(long millisUntilFinished) {
            long rest_time = millisUntilFinished / 1000;
            long hours = rest_time / 3600;
            long minutes = (rest_time - hours * 3600) / 60;
            long seconds = rest_time - hours * 3600 - minutes * 60;
            String sHours = String.valueOf(hours);
            String sMinutes = String.valueOf(minutes);
            String sSeconds = String.valueOf(seconds);
            if (hours < 10) {
                sHours = "0" + sHours;
            }
            if (minutes < 10) {
                sMinutes = "0" + sMinutes;
            }
            if (seconds < 0) {
                sSeconds = "00";
            } else if (seconds < 10) {
                sSeconds = "0" + sSeconds;
            }
            textView.setText("倒计时：" + sHours + "时 " + sMinutes + "分 " + sSeconds + "秒");
        }
    }

}
