package com.kexie.acloud.util;

/**
 * Created : wen
 * DateTime : 2017/5/6 21:48
 * Description : 处理Toekn的工具类
 */
public class TokenManager {

    public static String createToken(String userId) {
        return userId;
    }

    public static boolean checkToken(String token) {
        return true;
    }

    public static String getTokenValue(String token) {
        return token;
    }
}
