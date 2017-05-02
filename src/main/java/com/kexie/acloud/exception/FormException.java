package com.kexie.acloud.exception;

import org.springframework.validation.BindingResult;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created : wen
 * DateTime : 2017/5/2 10:08
 * Description :
 */
public class FormException extends Exception {

    BindingResult mBindingResult;

    public FormException() {
    }

    public FormException(BindingResult result) {
        mBindingResult = result;
    }

    public BindingResult getBindingResult() {
        return mBindingResult;
    }

    public void setBindingResult(BindingResult bindingResult) {
        mBindingResult = bindingResult;
    }

    public Map<String, String> getErrorForm() {

        Map<String, String> error = new LinkedHashMap<>();

        mBindingResult.getFieldErrors().stream().forEach(field -> {
            error.put(field.getField(), field.getDefaultMessage());
        });

        return error;
    }
}
