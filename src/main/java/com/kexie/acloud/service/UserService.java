package com.kexie.acloud.service;

import com.kexie.acloud.dao.IUserDao;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.UserException;
import com.kexie.acloud.util.DateUtil;
import com.kexie.acloud.util.EncryptionUtil;
import com.kexie.acloud.util.MyJedisConnectionFactory;
import com.kexie.acloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/8 13:43
 * Description :
 */
@Component("UserService")
@Transactional
public class UserService implements IUserService {

    @Autowired
    MyJedisConnectionFactory jedisConnectionFactory;

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
        Calendar calendar = Calendar.getInstance();
        // 用户今日第一次登录,积分+2
        if (!DateUtil.formatDate(calendar).equals(RedisUtil.getLastLoginDate(jedisConnectionFactory.getJedis(), loginUser.getUserId()))) {
            RedisUtil.updateLoginDate(jedisConnectionFactory.getJedis(), loginUser.getUserId(), DateUtil.formatCurrentDate());
            List<Society> societies = mUserDao.getSocietiesByUserId(loginUser.getUserId());
            for (Society society : societies) {
                RedisUtil.updateScoreboard(jedisConnectionFactory.getJedis(),
                        society.getId(),
                        loginUser.getUserId(),
                        loginUser.getNickName(),
                        2);
            }
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
        User user1 = mUserDao.updateUser(user);
        return user1;
    }

    @Override
    public void updateUserLogo(String userId, String relativePath) {
        User user = new User();
        user.setUserId(userId);
        user.setLogoUrl(relativePath);
        update(user);
    }

    /**
     * 通过id或者昵称搜索用户
     *
     * @param query 搜索的关键字
     */
    @Override
    public List<User> searchUser(String query) {
        return mUserDao.getUserBySearch(query);
    }
}
