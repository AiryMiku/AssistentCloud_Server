package com.kexie.acloud.dao;


import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/8 14:21
 * Description :
 */
public interface IUserDao {

    /**
     * 通过 Id 获取 TestUser 对象
     */
    User getUser(String userId);

    /**
     * 是否有userId的用户
     *
     * @deprecated 可以直接使用getUser() == null 判断数据库是否有这个User对象
     */
    @Deprecated
    boolean hasUserById(String userId);

    /**
     * 添加一个用户
     *
     * @param user
     */
    void addUser(User user);

    /**
     * 更新用户信息
     */
    void updateUser(User user);

    /**
     * 删除所有的数据，测试可以用
     */
    void clear();

    List<User> getUserBySociety(int society_id);

    /**
     * 获取用户拥有的社团
     *
     * @param userId
     * @return
     */
    List<Society> getSocietiesByUserId(String userId);
}
