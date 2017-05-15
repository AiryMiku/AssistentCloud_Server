package com.kexie.acloud.service;

import com.kexie.acloud.domain.Room;

import java.util.List;

/**
 * Created : wen
 * DateTime : 2017/5/12 2:00
 * Description : 多人聊天
 */
public interface IIMService {
    Room getRoomInfo(int roomId);

    int createRoom(Room room);

    List<Room> getRoomsByUserId(String userId);
}
