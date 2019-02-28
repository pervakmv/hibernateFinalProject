package service;

import model.Hotel;
import repository.HotelDAO;
import repository.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private HotelDAO hotelDAO = new HotelDAO();

    public Hotel addHotel(Hotel hotel) throws Exception {
        //check login
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addHotel: You need login");

        if (!UserDAO.logenedUserHasAdminPermit())
            throw new Exception("addRoom: User has not enugh permits");

        //if hotel is exist in repo
        if (!hotelDAO.findHotelByName(hotel.getName()).isEmpty() &&
                !hotelDAO.findHotelByCity(hotel.getCity()).isEmpty())
            throw new Exception("Hotel with name " + hotel.getName() + " is already exist" + " in the City : " + hotel.getCity());

        //check business logic
        if (!hotel.canBeAdd())
            throw new Exception("The entered hotel data can't be add");

        //if logic is ok
        return hotelDAO.save(hotel);

    }

    public Hotel deleteHotel(long hotelId) throws Exception {
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addHotel: You need login");

        if (!UserDAO.logenedUserHasAdminPermit())
            throw new Exception("addHotel: User has not enugh permits");
        Hotel hotel = hotelDAO.findById(hotelId);
        return hotelDAO.delete(hotel);
    }

    public List<Hotel> findHotelByName(String name) throws Exception {
        if (!UserDAO.logonWasSuccesful())
            throw new Exception("addHotel: You need login");

        return hotelDAO.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city) throws Exception {
        if (!UserDAO.logenedUserHasAdminPermit())
            throw new Exception("addHotel: User has not enugh permits");
        return hotelDAO.findHotelByCity(city);
    }


}
