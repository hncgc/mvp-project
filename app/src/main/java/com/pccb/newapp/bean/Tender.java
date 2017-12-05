package com.pccb.newapp.bean;

/**
 * Created by WangYi
 *
 * @Date : 2017/11/10
 * @Desc : 投资标
 */
public class Tender {
    //标的ID 整型
    private int id;
    //标的编码 字符串
    private String tenderCode;
    //标的标题 字符串
    private String title;
    //标的类型 字符串 {{limitTime:限时标,enterprise:华企通,eJiuding:E言九鼎}}
    private String type = "limitTime";
    private String typeText;
    //标的产品类型 字符串 {disperse:普通散标,finance:理财散标}
    private String tenderType;
    //标的借款金额 money类型
    private double amount;
    //标的真实募集金额 money类型
    private double amountRaised;
    //标的已预约金额 money类型
    private double amountAppointed;
    //标的展示年化率 整型
    private double rateShow;
    //收益区间
    private String rateShowMaximum;
    private String rateShowMinimum;

    //奖励抵用券比率展示
    private String bonusRateShow = "";

    //标的借款期限类型 字符串 {month:月,day:日}
    private String periodType;
    //标的借款周期 整型
    private int period;

    //可预约{yes:是,no:否}
    private String isAppointment;

    //标的还款方式 字符串 {avgInterest:等额本息,monthInterest:每月付息到期还本,onePrincipal:一次性本息}
    private String repaymentMode;

    //标的发布时间 字符串
    private String releaseTime;

    //标的状态 字符串  {待发标审核,待发标审核中,待发布,募集中,满标,还款中,已逾期,已还清,逾期还清,审核拒绝完结}
    //标的状态 字符串 {issuingAudit:待发标审核,issuingAuditIng:待发标审核中,
    //                pendingReleased:待发布,raiseing:募集中,fullScale:满标,
    //                repaymenting:还款中,overdue:已逾期,finish:已还清,
    //                finishOverdue:逾期还清,finishRefused:审核拒绝完结}
    private String status;

    //标的状态值，(给前端页面显示用) 字符串 {pendingReleased:等待发布,appointment:立即预约,buy:立即认购,fullScale:已满标,repaymenting:还款中,finish:已结束}
    private String statusStr;

    //标的募集结束时间	字符串
    private String raiseEndTime;

    //投资人数 整型
    private int investNumber;

    //是否显示征信标识 字符串 {yes:是,no:否}
    private String isCreditShow;

    //剩余可投金额 整型
    private double leftAmount;
    //起投金额
    private double startAmount;

    //标的累加金额 money类型
    private double stepAmount;

    //标的募集开始时间 字符串
    private String raiseStartTime;

    //项目完成百分比 字符串
    private double percent;

    //是否使用抵扣卡券	字符串 yes:可以,no:不可以
    private String isUseCouponDiscount;

    //是否奖励积分 字符串 {yes:是,no:否}
    //private String rewardPoint;

    //标的关联活动里的抵用券（奖） 字符串 false
    private boolean isCoupon = false;

    //标的关联活动奖励摘要,例如：(出借成功后奖X%抵用券)	字符串
    //private String rewardSummary = "";

    //关注人数 整型
    private int attentionNumber = 0;

    //投资天数 整型
    private int investDays;

    private String currServerTime;

    //是否可续投
    private boolean canContinuedBuy = false;

    // 产品类型 standard_tender:标准散标 loan_tender:借贷标 new_standard_tender:新标准散标 new_loan_tender:新借贷标
    private String productType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenderCode() {
        return tenderCode;
    }

    public void setTenderCode(String tenderCode) {
        this.tenderCode = tenderCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTenderType() {
        return tenderType;
    }

    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountRaised() {
        return amountRaised;
    }

    public void setAmountRaised(double amountRaised) {
        this.amountRaised = amountRaised;
    }

    public double getAmountAppointed() {
        return amountAppointed;
    }

    public void setAmountAppointed(double amountAppointed) {
        this.amountAppointed = amountAppointed;
    }

    public double getRateShow() {
        return rateShow;
    }

    public void setRateShow(double rateShow) {
        this.rateShow = rateShow;
    }

    public String getBonusRateShow() {
        return bonusRateShow;
    }

    public void setBonusRateShow(String bonusRateShow) {
        this.bonusRateShow = bonusRateShow;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getIsAppointment() {
        return isAppointment;
    }

    public void setIsAppointment(String isAppointment) {
        this.isAppointment = isAppointment;
    }

    public String getRepaymentMode() {
        return repaymentMode;
    }

    public void setRepaymentMode(String repaymentMode) {
        this.repaymentMode = repaymentMode;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getRaiseEndTime() {
        return raiseEndTime;
    }

    public void setRaiseEndTime(String raiseEndTime) {
        this.raiseEndTime = raiseEndTime;
    }

    public int getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(int investNumber) {
        this.investNumber = investNumber;
    }

    public String getIsCreditShow() {
        return isCreditShow;
    }

    public void setIsCreditShow(String isCreditShow) {
        this.isCreditShow = isCreditShow;
    }

    public double getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(double leftAmount) {
        this.leftAmount = leftAmount;
    }

    public double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(double startAmount) {
        this.startAmount = startAmount;
    }

    public double getStepAmount() {
        return stepAmount;
    }

    public void setStepAmount(double stepAmount) {
        this.stepAmount = stepAmount;
    }

    public String getRaiseStartTime() {
        return raiseStartTime;
    }

    public void setRaiseStartTime(String raiseStartTime) {
        this.raiseStartTime = raiseStartTime;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getIsUseCouponDiscount() {
        return isUseCouponDiscount;
    }

    public void setIsUseCouponDiscount(String isUseCouponDiscount) {
        this.isUseCouponDiscount = isUseCouponDiscount;
    }

    public boolean isCoupon() {
        return isCoupon;
    }

    public void setCoupon(boolean coupon) {
        isCoupon = coupon;
    }

    public int getAttentionNumber() {
        return attentionNumber;
    }

    public void setAttentionNumber(int attentionNumber) {
        this.attentionNumber = attentionNumber;
    }

    public int getInvestDays() {
        return investDays;
    }

    public void setInvestDays(int investDays) {
        this.investDays = investDays;
    }

    public String getCurrServerTime() {
        return currServerTime;
    }

    public void setCurrServerTime(String currServerTime) {
        this.currServerTime = currServerTime;
    }

    public boolean isCanContinuedBuy() {
        return canContinuedBuy;
    }

    public void setCanContinuedBuy(boolean canContinuedBuy) {
        this.canContinuedBuy = canContinuedBuy;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getRateShowMaximum() {
        return rateShowMaximum;
    }

    public void setRateShowMaximum(String rateShowMaximum) {
        this.rateShowMaximum = rateShowMaximum;
    }

    public String getRateShowMinimum() {
        return rateShowMinimum;
    }

    public void setRateShowMinimum(String rateShowMinimum) {
        this.rateShowMinimum = rateShowMinimum;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
