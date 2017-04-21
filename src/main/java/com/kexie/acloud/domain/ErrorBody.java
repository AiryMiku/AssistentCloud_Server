package com.kexie.acloud.domain;

/**
 * Created : wen
 * DateTime : 2017/4/11 23:43
 * Description :
 */
public class ErrorBody {
    String errorMessage;

    public ErrorBody(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
