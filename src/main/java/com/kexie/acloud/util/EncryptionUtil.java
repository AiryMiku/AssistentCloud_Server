package com.kexie.acloud.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import sun.misc.BASE64Encoder;

/**
 * Created : wen
 * DateTime : 16-12-17 下午7:54
 * Description :
 */
public class EncryptionUtil {

    public static MessageDigest mMd5;

    static {
        try {
            mMd5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String generateMD5(String message) {
        byte[] bytes = mMd5.digest(message.getBytes());
        return new BigInteger(1, bytes).toString(16);
    }

    /**
     * 生成16位的随机盐
     */
    public static String generateSalt() {
        Random random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return new BASE64Encoder().encode(salt);
    }

    /**
     * 校验密码
     */
    public static boolean verify(String password, String salt, String hash) {
        String pswSalt = generateMD5(password + salt);
        return hash.equals(pswSalt);
    }

}
