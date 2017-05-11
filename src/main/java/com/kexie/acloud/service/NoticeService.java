package com.kexie.acloud.service;

import com.kexie.acloud.dao.INoticeDao;
import com.kexie.acloud.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zojian on 2017/5/8.
 */
@Service
public class NoticeService implements INoticeService {
    @Autowired
    INoticeDao noticeDao;

    @Override
    public boolean addNotice(Notice notice) {
        return noticeDao.addNotice(notice);
    }

    @Override
    public boolean updateNotice(int notice_id,Notice newNotice) {
        return noticeDao.updateNotice(notice_id,newNotice);
    }

    @Override
    public boolean deleteNotice(int notice_id) {
        return noticeDao.deleteNotice(notice_id);
    }

    @Override
    public List<Notice> getNoticesByUserId(String user_id, int page, int pageSize) {
        return noticeDao.getNoticesByUserId(user_id,page,pageSize);
    }

    @Override
    public List<Notice> getNoticesByPublisherId(String publisher_id, int page, int pageSize) {
        return noticeDao.getNoticesByPublisherId(publisher_id,page,pageSize);
    }

    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(String user_id, int society_id, int page,int pageSize) {
        return noticeDao.getNoticesByUserIdAndSocietyId(user_id,society_id,page,pageSize);
    }

    @Override
    public Notice getNoticeByNoticeId(int notice_id) {
        return null;
    }
}
