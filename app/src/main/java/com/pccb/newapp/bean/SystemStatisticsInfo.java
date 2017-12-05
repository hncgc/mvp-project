package com.pccb.newapp.bean;

import java.io.Serializable;

/**
 * Created by YanJun on 2017/6/12.
 */

public class SystemStatisticsInfo implements Serializable {

    private String resultCode;
    private boolean success;
    private String resultMessage;
    private String totalInvestMoney;// 	累计成交金额 	money类型 	否 	否 	累计成交金额
    private String totalInterest;// 	赚取收益 	money类型 	否 	否 	赚取收益
    private Long times;// 	平台安全运行时间:单位毫秒 	整型 	否 	否 	平台安全运行时间:...
    private Float yearSettlementRate;// 	年化结算率,保留两位小数 	对象类型 	否 	否 	年化结算率,保留两...
    private Long registerNum;// 	注册人数 	整型 	否 	否 	注册人数
    private Long puzicarInvestPeopleNum;// 	普资汽车出借人数 	整型 	否 	否 	普资汽车出借人数
    private String puzicarInvestInfoDate;// 	普资汽车信息统计日期 	字符串 	否 	否 	普资汽车信息统计日...
    private Long puzicarInvestNum;// 	普资汽车成交笔数 	整型 	否 	否 	普资汽车成交笔数
    private String puzicarTotalInvestMoney;//

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getTotalInvestMoney() {
        return totalInvestMoney;
    }

    public void setTotalInvestMoney(String totalInvestMoney) {
        this.totalInvestMoney = totalInvestMoney;
    }

    public String getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(String totalInterest) {
        this.totalInterest = totalInterest;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Float getYearSettlementRate() {
        return yearSettlementRate;
    }

    public void setYearSettlementRate(Float yearSettlementRate) {
        this.yearSettlementRate = yearSettlementRate;
    }

    public Long getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Long registerNum) {
        this.registerNum = registerNum;
    }

    public Long getPuzicarInvestPeopleNum() {
        return puzicarInvestPeopleNum;
    }

    public void setPuzicarInvestPeopleNum(Long puzicarInvestPeopleNum) {
        this.puzicarInvestPeopleNum = puzicarInvestPeopleNum;
    }

    public String getPuzicarInvestInfoDate() {
        return puzicarInvestInfoDate;
    }

    public void setPuzicarInvestInfoDate(String puzicarInvestInfoDate) {
        this.puzicarInvestInfoDate = puzicarInvestInfoDate;
    }

    public Long getPuzicarInvestNum() {
        return puzicarInvestNum;
    }

    public void setPuzicarInvestNum(Long puzicarInvestNum) {
        this.puzicarInvestNum = puzicarInvestNum;
    }

    public String getPuzicarTotalInvestMoney() {
        return puzicarTotalInvestMoney;
    }

    public void setPuzicarTotalInvestMoney(String puzicarTotalInvestMoney) {
        this.puzicarTotalInvestMoney = puzicarTotalInvestMoney;
    }
}
