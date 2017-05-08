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
    public boolean updateNotice(Notice notice) {
        return false;
    }

    @Override
    public boolean deleteNotice(Notice notice) {
        return false;
    }

    @Override
    public List<Notice> getNoticesByUserId(int user_id, int page) {
        return null;
    }

    @Override
    public List<Notice> getNoticesByPublisherId(int publisher_id, int page) {
        return null;
    }

    @Override
    public List<Notice> getNoticesByUserIdAndSocietyId(int user_id, int society_id, int page) {
        return null;
    }

    @Override
    public Notice getNoticeByNoticeId(int notice_id) {
        return null;
    }
}
