package com.kexie.acloud.service;

import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;

/**
 * Created : wen
 * DateTime : 2017/5/12 0:51
 * Description :
 */
public interface IMeetingService {
    Room createMeeting(Meeting meeting, User creator) throws AuthorizedException;
}
