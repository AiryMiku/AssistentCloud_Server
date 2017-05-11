package com.kexie.acloud.service;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.SocietyException;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/9 23:47
 * Description :
 */
public interface ISocietyService {

    /**
     * 添加社团
     *
     * @param society
     * @return
     */
    void add(Society society) throws SocietyException;

    /**
     * 更新社团信息
     *
     * @param society
     */
    void updateSociety(Society society);

    /**
     * 根据社团ID获取社团信息
     *
     * @param society_id 社团ID
     * @return
     */
    Society getSocietyById(int society_id);

    /**
     * 获取当前社团的所有成员
     *
     * @param society_id
     * @return
     */
    List<User> getUsersIn(int society_id);

    /**
     * 获取所有的社团信息
     *
     * @param schoolId
     */
    List<Society> getSoicetiesBySchoolId(int schoolId);

    List<Society> getSoicetiesByCollegeId(int collegeId);

    List<Society> getSocietiesByUserId(String userId);
}

