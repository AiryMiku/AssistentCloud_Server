package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Society;

/**
 * Created : wen
 * DateTime : 2017/4/25 19:19
 * Description :
 */
public interface ISocietyDao {

    /**
     * 添加社团
     * @param society
     * @return
     */
    public boolean add(Society society);

    /**
     * 根据社团ID获取社团信息
     * @param society_id 社团ID
     * @return
     */
    public Society getSocietyById(int society_id);
}
