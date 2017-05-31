package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyApply;
import com.kexie.acloud.domain.SocietyPosition;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:19
 * Description :
 */
public interface ISocietyDao {

    /**
     * 数据库中是否有社团
     *
     * @param societyName 社团名字
     * @param collegeId   学院Id
     * @return
     */
    boolean hasSociety(String societyName, int collegeId);

    boolean hasSociety(int societyId);

    /**
     * 添加社团
     *
     * @param society
     * @return
     */
    public void add(Society society);

    /**
     * 根据社团ID获取社团信息
     *
     * @param society_id 社团ID
     * @return
     */
    public Society getSocietyById(int society_id);

    List<Society> getSocietiesBySchoolId(int schoolId);

    List<Society> getSocietiesByCollegeId(int collegeId);

    void update(Society society);

    List<Society> getSocietiesByName(String query);

    SocietyPosition getSocietyPositionByUserId(String userId, int societyId);

    /**
     * 用户是否在社团中
     *
     * @param societyId
     * @param userId
     * @return
     */
    boolean isInSociety(int societyId, String userId);

    void addNewMember(int positionId, String userId);

    void addApply(SocietyApply apply);

    List<SocietyApply> getAllSocietyApply(Integer societyId);

    SocietyApply getSocietyApply(int applyId);

    void deleteSocietyApply(int applyId);

    SocietyPosition getLowestPosition(Society society);

    List<SocietyPosition> getSocietyPosition(int societyId);

    void deleteMember(String societyId, String userId);
}
