package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Room;

/**
 * Created : wen
 * DateTime : 2017/5/12 10:40
 * Description :
 */
public interface IRoomDao {
    Room getRoom(int roomId);

    Integer addRoom(Room room);

    void updateRoom(Room room);

    void deleteRoom(int roomId);
}
