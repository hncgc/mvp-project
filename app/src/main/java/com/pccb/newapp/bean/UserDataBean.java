package com.pccb.newapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

/**
 * 用户数据
 * @author cgc
 * @created 2017-11-06
 */
@Entity
public class UserDataBean  implements Serializable {
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Long user_id;      //用户ID
    private String picture_url;  //用户头像
    private String real_name;    //姓名
    private String nick_name;    //昵称,用户名
    private String phone_num;    //手机号码
    private Boolean islogin;     //登陆标示
    private int category_id;  //有权发帖的论坛版块id
    private int is_moderator; //是否是版主
    private String remark;       //备注或者预留
    private String username;
    private String email;//邮箱
    private String grade;//用户等级
    private String realNameLevel;//用户实名状态   LV00:未实名   LV01:已实名
    private String cardNo;
    private String version;
    private String appClient;
    private String partnerId;
    private String secretKey;
    private String accessKey;
    private boolean isBandCard;

    @Generated(hash = 1792504933)
    public UserDataBean(Long user_id, String picture_url, String real_name, String nick_name, String phone_num,
            Boolean islogin, int category_id, int is_moderator, String remark, String username, String email,
            String grade, String realNameLevel, String cardNo, String version, String appClient, String partnerId,
            String secretKey, String accessKey, boolean isBandCard) {
        this.user_id = user_id;
        this.picture_url = picture_url;
        this.real_name = real_name;
        this.nick_name = nick_name;
        this.phone_num = phone_num;
        this.islogin = islogin;
        this.category_id = category_id;
        this.is_moderator = is_moderator;
        this.remark = remark;
        this.username = username;
        this.email = email;
        this.grade = grade;
        this.realNameLevel = realNameLevel;
        this.cardNo = cardNo;
        this.version = version;
        this.appClient = appClient;
        this.partnerId = partnerId;
        this.secretKey = secretKey;
        this.accessKey = accessKey;
        this.isBandCard = isBandCard;
    }

    @Generated(hash = 1684300321)
    public UserDataBean() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public Boolean getIslogin() {
        return islogin;
    }

    public void setIslogin(Boolean islogin) {
        this.islogin = islogin;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getIs_moderator() {
        return is_moderator;
    }

    public void setIs_moderator(int is_moderator) {
        this.is_moderator = is_moderator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRealNameLevel() {
        return realNameLevel;
    }

    public void setRealNameLevel(String realNameLevel) {
        this.realNameLevel = realNameLevel;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppClient() {
        return appClient;
    }

    public void setAppClient(String appClient) {
        this.appClient = appClient;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public boolean isBandCard() {
        return isBandCard;
    }

    public void setBandCard(boolean bandCard) {
        isBandCard = bandCard;
    }

    public boolean getIsBandCard() {
        return this.isBandCard;
    }

    public void setIsBandCard(boolean isBandCard) {
        this.isBandCard = isBandCard;
    }
}
