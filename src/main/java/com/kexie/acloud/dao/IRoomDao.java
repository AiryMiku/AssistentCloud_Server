package com.kexie.acloud.dao;

import com.kexie.acloud.domain.Room;

import java.util.List;

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

    void clearSession();

    List<Room> getRoomsByUserId(String userId);
}
