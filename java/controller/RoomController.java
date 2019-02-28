package controller;

import model.Filter;
import model.Room;
import service.RoomService;

import java.util.List;

public class RoomController {
    private RoomService roomService = new RoomService();

    public Room addRoom(Room room) throws Exception {
        return roomService.addRoom(room);
    }

    public Room deleteRoom(long roomId) throws Exception {
        return roomService.deleteRoom(roomId);
    }

    public void bookRoom(long roomId, long userId, long hotelId) throws Exception {
        roomService.bookRoom(roomId, userId, hotelId);
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        roomService.cancelReservation(roomId, userId);
    }

    public List<Room> findRooms(Filter filter) throws Exception {
        return roomService.findRooms(filter);
    }
}
