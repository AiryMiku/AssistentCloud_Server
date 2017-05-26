package com.kexie.acloud.service;

import com.kexie.acloud.dao.INoticeDao;
import com.kexie.acloud.domain.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
    public boolean updateNotice(int notice_id,Notice newNotice, String user_id) {
        return noticeDao.updateNotice(notice_id,newNotice,user_id);
    }

    @Override
    public boolean deleteNotice(int notice_id, String user_id) {
        return noticeDao.deleteNotice(notice_id, user_id);
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
    public Notice getNoticeByNoticeId(int notice_id,String user_id) {
        return noticeDao.getNoticeByNoticeId(notice_id,user_id);
    }

    @Override
    public Set<String> getNoticeVisitorByNoticeId(int notice_id) {
        return noticeDao.getNoticeVisitorByNoticeId(notice_id);
    }
}
