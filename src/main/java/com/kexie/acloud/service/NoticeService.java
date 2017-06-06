package com.kexie.acloud.service;

import com.kexie.acloud.dao.INoticeDao;
import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.domain.Notice;
import com.kexie.acloud.exception.NoticeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Set;

/**
 * Created by zojian on 2017/5/8.
 */
@Service
public class NoticeService implements INoticeService {
    @Resource
    ISocietyDao mSocietyDao;

    @Autowired(required = false)
    INoticeDao noticeDao;

    @Override
    public boolean addNotice(Notice notice,String userId) throws AuthenticationException {
//        if(!mSocietyDao.isInSociety(notice.getSociety().getId(),userId)){
//            throw new AuthenticationException("用户 " + userId + " 不在当前社团 " + notice.getSociety().getName() + " 中");
//        }
//        if (!mSocietyDao.isInSociety(notice.getSociety().getId(), notice.getExecutors())) {
//            throw new AuthenticationException("有一些执行者不在当前社团 " + notice.getSociety().getName() + " 中");
//        }
        return noticeDao.addNotice(notice,userId);
    }

    @Override
    public boolean updateNotice(int notice_id,Notice newNotice, String user_id) throws NoticeException {
        if(noticeDao.getNoticeByNoticeId(notice_id,user_id,null)==null){
            throw new NoticeException("公告不存在,公告ID有误");
        }
        return noticeDao.updateNotice(notice_id,newNotice,user_id);
    }

    @Override
    public boolean deleteNotice(int notice_id, String user_id) throws NoticeException {
        if(noticeDao.getNoticeByNoticeId(notice_id,user_id,null)==null){
            throw new NoticeException("公告不存在,公告ID有误");
        }
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
    public Notice getNoticeByNoticeId(int notice_id,String user_id,String identifier) throws NoticeException {
        return noticeDao.getNoticeByNoticeId(notice_id,user_id,identifier);
    }

    @Override
    public Set<String> getNoticeVisitorByNoticeId(int notice_id, String user_id) throws NoticeException {
        return noticeDao.getNoticeVisitorByNoticeId(notice_id,user_id);
    }
}
