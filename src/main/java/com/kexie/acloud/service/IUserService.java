package com.kexie.acloud.service;


import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;


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
}
