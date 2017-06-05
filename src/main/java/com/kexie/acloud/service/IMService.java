package com.kexie.acloud.service;

import com.kexie.acloud.dao.IRoomDao;
import com.kexie.acloud.domain.Room;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

/**
 * Created : wen
 * DateTime : 2017/5/12 2:00
 * Description :
 */
@Service
@Transactional
public class IMService implements IIMService {

    @Resource
    private IRoomDao mRoomDao;

    @Override
    public Room getRoomInfo(int roomId) {
        Room room = mRoomDao.getRoom(roomId);
        // todo 处理懒加载
        System.out.println(room);
        return room;
    }

    @Override
    public int createRoom(Room room) {
        return mRoomDao.addRoom(room);
    }

    @Override
    public List<Room> getRoomsByUserId(String userId) {
        return mRoomDao.getRoomsByUserId(userId);
    }
}
