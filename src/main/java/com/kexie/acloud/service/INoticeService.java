package com.kexie.acloud.service;

import com.kexie.acloud.domain.Notice;

import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
public interface INoticeService {
    /**
     * 发布公告
     * @param notice 公告对象
     * @return
     */
    public boolean addNotice(Notice notice);

    /**
     * 更新公告
     * @param notice
     * @return
     */
    public boolean updateNotice(Notice notice);

    /**
     * 删除公告
     * @param notice
     * @return
     */
    public boolean deleteNotice(Notice notice);

    /**
     * 根据用户ID分页获取所以用户可见的公告列表
     * @param user_id 用户ID
     * @param page 页数
     * @return
     */
    public List<Notice> getNoticesByUserId(int user_id, int page);

    /**
     * 根据发布者ID分页获取由用户发布的公告
     * @param publisher_id 发布者ID(即当前的user_id)
     * @param page
     * @return
     */
    public List<Notice> getNoticesByPublisherId(int publisher_id,int page);

    /**
     * 根据用户ID和社团ID分页获取公告列表
     * @param user_id 用户ID
     * @param society_id 社团ID
     * @param page
     * @return
     */
    public List<Notice> getNoticesByUserIdAndSocietyId(int user_id,int society_id,int page);

    /**
     * 根据公告ID获取公告详细信息
     * @param notice_id 公告ID
     * @return
     */
    public Notice getNoticeByNoticeId(int notice_id);
}
