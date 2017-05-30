package com.kexie.acloud.service;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;
import com.kexie.acloud.exception.SocietyException;
import com.kexie.acloud.exception.UserException;

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
    Society getSocietyById(int society_id) throws SocietyException;

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

    void updateSocietyLogo(int societyId, String relativePath);

    void changePrincipal(String oldId, String newId, int societyId) throws UserException;

    List<Society> searchSocietyByName(String query);

    void applyJoinSociety(SocietyApply apply) throws SocietyException;

    /**
     * 获取社团所有的申请请求
     *
     * @param societyId
     * @param userId
     * @return
     */
    List<SocietyApply> getSocietyApplyIn(String societyId, String userId) throws SocietyException, AuthorizedException;

    /**
     * 处理一个社团请求
     */
    void handleSocietyApple(String applyId, boolean isAllow, String userId) throws SocietyException, AuthorizedException;

    void quitSociety(String societyId, String userId);
}

