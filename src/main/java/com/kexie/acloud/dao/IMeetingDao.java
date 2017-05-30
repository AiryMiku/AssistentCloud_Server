package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Meeting;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/12 14:18
 * Description :
 */
public interface IMeetingDao {
    void addMeeting(Meeting meeting);

    List<Meeting> getCurrentUserNotStartMeeting(String userId);
}
