package service;

import model.Filter;
import model.Room;
import repository.RoomDAO;
import repository.UserDAO;

import java.util.List;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public Room addRoom(Room room) throws Exception {
        //check logon
        System.out.println(UserDAO.logenedUser.getUsername());
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addRoom: You need login");

        if (!UserDAO.logenedUserHasAdminPermit())
            throw new Exception("addRoom: User has not enough permits");

        //check business logic
        if (!room.canBeAdd())
            throw new Exception("addRoom: The entered room data can't be add");
        return roomDAO.save(room);
    }

    public Room deleteRoom(long roomId) throws Exception {
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addRoom: You need login");

        if (!UserDAO.logenedUserHasAdminPermit())
            throw new Exception("addRoom: User has not enough permits");

        Room room = roomDAO.findById(roomId);
        return roomDAO.delete(room);
    }

    public void bookRoom(long roomId, long userId, long hotelId) throws Exception {
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addRoom: You need login");

        roomDAO.bookRoom(roomId, userId, hotelId);
    }

    public void cancelReservation(long roomId, long userId) throws Exception{
        if(!UserDAO.logonWasSuccesful())
            throw new Exception("addRoom: You  need login");

        roomDAO.cancelReservation(roomId, userId);
    }

    public List<Room> findRooms(Filter filter) throws Exception{
        if(!UserDAO.logonWasSuccesful())
            throw new Exception("addRoom: You need login");
        return roomDAO.findRooms(filter);
    }
}
