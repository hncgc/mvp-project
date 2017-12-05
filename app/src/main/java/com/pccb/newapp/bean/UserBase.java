package com.pccb.newapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户数据
 * @author cgc
 * @created 2017-11-06
 */
@Entity
public class UserBase {
    @Id
    private Long user_id;      //用户ID
    private String phone_num;    //手机号码

    @Generated(hash = 156165868)
    public UserBase(Long user_id, String phone_num) {
        this.user_id = user_id;
        this.phone_num = phone_num;
    }

    @Generated(hash = 174036696)
    public UserBase() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }
}
