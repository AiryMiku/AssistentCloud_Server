package com.kexie.acloud.service;


import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;

import java.util.List;


/**
 * Created : wen
 * DateTime : 2017/4/8 13:42
 * Description :
 */
public interface IUserService {
    User login(User user) throws UserException;

    User register(User user) throws UserException;

    User getUserByUserId(String userId) throws UserException;

    User update(User user);

    void updateUserLogo(String userId, String relativePath);

    /**
     * 通过id或者昵称搜索用户
     *
     * @param query 搜索的关键字
     */
    List<User> searchUser(String query);
}
