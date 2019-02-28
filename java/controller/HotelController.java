package controller;

import model.Hotel;
import service.HotelService;

import java.util.ArrayList;
import java.util.List;

public class HotelController {
    private HotelService hotelService = new HotelService();

    public Hotel addHotel(Hotel hotel) throws Exception {
        return hotelService.addHotel(hotel);
    }

    public Hotel deleteHotel(long hotelId) throws Exception {
        return hotelService.deleteHotel(hotelId);
    }

    public List<Hotel> findHotelByName(String name) throws Exception {
        return hotelService.findHotelByName(name);
    }

    public List<Hotel> findHotelByCity(String city) throws Exception {
        return hotelService.findHotelByCity(city);
    }
}
