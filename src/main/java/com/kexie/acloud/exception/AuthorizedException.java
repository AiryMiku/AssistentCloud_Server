package com.kexie.acloud.exception;

/**
 * Created : wen
 * DateTime : 2017/5/7 23:03
 * Description : 权限异常，可能是没有登录
 */
public class AuthorizedException extends Exception {

    public AuthorizedException(String msg) {
        super(msg);
    }
}
