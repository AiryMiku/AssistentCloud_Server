package com.kexie.acloud.service;

import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.util.BeanUtil;
import com.kexie.acloud.util.EncryptionUtil;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:43
 * Description :
 */
@Component("UserService")
@Transactional
public class UserService implements IUserService {

    @Resource(name = "UserDao")
    private IUserDao mUserDao;

    @Override
    public User login(User user) throws UserException {

        User loginUser = mUserDao.getUser(user.getUserId());

        if (loginUser == null)
            throw new UserException("用户不存在");

        if (!EncryptionUtil.verify(user.getPassword(), loginUser.getSalt(), loginUser.getHash())) {
            throw new UserException("账号或密码错误");
        }

        return loginUser;
    }

    @Override
    public User register(User user) throws UserException {

        if (mUserDao.hasUserById(user.getUserId()))
            throw new UserException("用户已经被注册了");

        // 加密
        String salt = EncryptionUtil.generateSalt();
        user.setSalt(salt);
        user.setHash(EncryptionUtil.generateMD5(user.getPassword() + salt));

        mUserDao.addUser(user);

        return mUserDao.getUser(user.getUserId());
    }

    @Override
    public User getUserByUserId(String userId) throws UserException {

        User user = mUserDao.getUser(userId);

        if (user == null)
            throw new UserException("用户不存在");

        return user;
    }

    @Override
    public User update(User user) {
        User u = mUserDao.getUser(user.getUserId());
        BeanUtil.copyProperties(user, u);
        mUserDao.updateUser(u);
        return u;
    }

    @Override
    public void updateUserLogo(String userId, String relativePath) {
        User user = new User();
        user.setUserId(userId);
        user.setLogoUrl(relativePath);
        update(user);
    }
}
