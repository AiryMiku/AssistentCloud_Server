package com.kexie.acloud.util;

import com.kexie.acloud.domain.User;

/**
 * Created : wen
 * DateTime : 2017/4/12 0:14
 * Description :
 */
public class UserUtil {

    /**
     * 获取放回给客户端的用户字段
     * @param user
     * @return
     */
    public static User getCilentUserField(User user){

        User newUser = new User();

        newUser.setNickName(user.getNickName());
        newUser.setUserId(user.getUserId());

        return newUser;
    }
}
