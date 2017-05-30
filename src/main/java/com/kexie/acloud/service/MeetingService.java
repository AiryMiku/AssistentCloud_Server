package com.kexie.acloud.service;

import com.kexie.acloud.dao.IMeetingDao;
import com.kexie.acloud.dao.IRoomDao;
import com.kexie.acloud.dao.ISocietyDao;
import com.kexie.acloud.domain.Meeting;
import com.kexie.acloud.domain.Room;
import com.kexie.acloud.domain.Society;
import com.kexie.acloud.domain.SocietyPosition;
import com.kexie.acloud.domain.User;
import com.kexie.acloud.exception.AuthorizedException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Resource
    private ISocietyDao mSocietyDao;

    /**
     * 创建会议
     *
     * @param meeting
     * @param creator
     * @return
     */
    @Override
    public Room createMeeting(Meeting meeting, User creator) throws AuthorizedException {

        // 创建者是否有权限创建会议
        SocietyPosition position = mSocietyDao.getSocietyPositionByUserId(creator.getUserId(), meeting.getSociety().getId());
        if (!hasCreateMeetingPermission(position)) {
            throw new AuthorizedException("当前用户没有权限创建会议（他的职位没有主席两个字）");
        }

        // 判断参与者是否存在于社团中
        List<User> members = meeting.getMembers();
        Society society = meeting.getSociety();
        for (User member : members) {
            if (!mSocietyDao.isInSociety(society, member)) {
                throw new AuthorizedException("用户 = " + member.getUserId() + " 不在社团 = " + society.getId() + " 中");
            }
        }

        // 主题作为房间名
        Room room = new Room();
        room.setName(meeting.getName());
        room.setMaster(creator);
        room.setMember(meeting.getMembers());

        // 创建一个房间
        int roomId = mRoomDao.addRoom(room);
        room.setRoomId(roomId);

        // 添加一个会议
        meeting.setPublisher(creator);
        meeting.setRoom(room);
        mMeetingDao.addMeeting(meeting);

        // 清除session，不然得到的user数据为空
        mRoomDao.clearSession();

        return mRoomDao.getRoom(room.getRoomId());
    }

    @Override
    public List<Meeting> getCurrentUserNotStartMeeting(String userId) {
        return mMeetingDao.getCurrentUserNotStartMeeting(userId);
    }

    /**
     * 当前社团职位是否有权利去创建会议
     *
     * @param position
     * @return
     */
    private boolean hasCreateMeetingPermission(SocietyPosition position) {
        return position.getName().contains("主席");
    }


}
