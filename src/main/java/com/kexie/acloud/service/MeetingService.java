package com.kexie.acloud.service;

import com.kexie.acloud.dao.IMeetingDao;
import com.kexie.acloud.dao.IRoomDao;
import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/12 0:51
 * Description :
 */
@Service
@Transactional
public class MeetingService implements IMeetingService {

    @Resource
    private IMeetingDao mMeetingDao;

    @Resource
    private IRoomDao mRoomDao;

    @Override
    public Room createMeeting(Meeting meeting) {

        // 发布者
        User master = meeting.getPublisher();

        // 主题作为房间名
        Room room = new Room();
        room.setName(meeting.getName());
        room.setMaster(master);
        room.setMember(meeting.getMembers());

        // 创建一个房间
        int roomId = mRoomDao.addRoom(room);
        room.setRoomId(roomId);
        meeting.setRoom(room);

        // 添加一个会议
        mMeetingDao.addMeeting(meeting);

        return room;
    }
}
