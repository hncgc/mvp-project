package com.pccb.newapp.bean;

import java.io.Serializable;

/**
 * Created by YanJun on 2017/8/4.
 */

public class BaseResponse implements Serializable {
    private String resultCode;
    private boolean success;
    private String resultMessage;

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
}
