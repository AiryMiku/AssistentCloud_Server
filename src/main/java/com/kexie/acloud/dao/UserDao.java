package com.kexie.acloud.dao;


import com.kexie.acloud.domain.User;

/**
 * Created : wen
 * DateTime : 2017/4/8 14:21
 * Description :
 */
public interface UserDao {

    User getUser(String userId);

    boolean hasUserById(String userId);

    void addUser(User user);

    void updateUser(User user);

    void clear();
}
