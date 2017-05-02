package com.kexie.acloud.domain;

/**
 * Created : wen
 * DateTime : 2017/4/11 23:43
 * Description :
 */
public class ErrorBody {
    String msg;

    public ErrorBody(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
