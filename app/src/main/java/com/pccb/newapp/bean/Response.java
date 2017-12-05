package com.pccb.newapp.bean;

/**
 * Created by WangYi
 *
 * @Date : 2017/10/11
 * @Desc : 公共响应参数
 */
public class Response extends BaseResponse {
    private boolean appClient;
    private String protocol;
    private String service;
    private String sign;
    private String signType;
    private String partnerId;
    private String requestNo;
    private String version;
    private String resultDetail;
    private String merchOrderNo;

    public boolean isAppClient() {
        return appClient;
    }

    public void setAppClient(boolean appClient) {
        this.appClient = appClient;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResultDetail() {
        return resultDetail;
    }

    public void setResultDetail(String resultDetail) {
        this.resultDetail = resultDetail;
    }

    public String getMerchOrderNo() {
        return merchOrderNo;
    }

    public void setMerchOrderNo(String merchOrderNo) {
        this.merchOrderNo = merchOrderNo;
    }
}
