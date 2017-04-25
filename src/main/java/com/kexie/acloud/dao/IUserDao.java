package com.kexie.acloud.dao;


import com.kexie.acloud.domain.User;

/**
 * Created : wen
 * DateTime : 2017/4/8 14:21
 * Description :
 */
public interface IUserDao {

    /**
     * 通过 Id 获取 User 对象
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
}
