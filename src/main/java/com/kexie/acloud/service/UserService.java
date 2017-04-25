package com.kexie.acloud.service;

import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.util.EncryptionUtil;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:43
 * Description :
 */
@Component("UserService")
public class UserService implements IUserService {

    @Resource(name = "UserDao")
    private IUserDao mIUserDao;

    @Override
    public User login(User user) throws UserException {

        User loginUser = mIUserDao.getUser(user.getUserId());

        if (loginUser == null)
            throw new UserException("用户不存在");

        if (!EncryptionUtil.verify(user.getPassword(), loginUser.getSalt(), loginUser.getHash())) {
            throw new UserException("账号或密码错误");
        }

        return loginUser;
    }

    @Override
    public User register(User user) throws UserException {

        if (mIUserDao.hasUserById(user.getUserId()))
            throw new UserException("用户已经被注册了");

        // 加密
        String salt = EncryptionUtil.generateSalt();
        user.setSalt(salt);
        user.setHash(EncryptionUtil.generateMD5(user.getPassword() + salt));

        mIUserDao.addUser(user);

        return mIUserDao.getUser(user.getUserId());
    }

    @Override
    public User getUserByUserId(String userId) throws UserException {

        User user =  mIUserDao.getUser(userId);

        if(user == null)
            throw new UserException("用户不存在");

        return user;
    }
}
