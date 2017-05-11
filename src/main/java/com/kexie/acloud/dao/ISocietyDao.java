package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;

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


}
