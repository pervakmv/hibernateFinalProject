package demo;

import model.*;
import repository.HotelDAO;
import repository.OrderDAO;
import repository.RoomDAO;
import repository.UserDAO;

import java.util.Date;

public class DemoDAO {
    public static void main(String[] args) throws Exception{
        UserDAO userDAO = new UserDAO();
        RoomDAO roomDAO = new RoomDAO();
        OrderDAO orderDAO = new OrderDAO();
        HotelDAO hotelDAO = new HotelDAO();


        User user1 = new User("Коля", "2278","Укаїна", UserType.ADMIN, null);
        User user2 = new User("Маша", "2279","Болівія", UserType.USER, null);
        User user3 = new User("Святослав", "2280", "Туркменія", UserType.USER, null);


        Hotel hotel = new Hotel("Кобиляки", "Україна", "Полтава", "Шевченка", null);
        Hotel hotel2 = new Hotel("Елас", "Україна", "Полтава", "Транспортна", null);
        //hotelDAO.save(hotel);
        //hotelDAO.save(hotel2);
        //System.out.println(hotelDAO.findHotelByName("Сакура"));

//        userDAO.save(user1);
//        userDAO.save(user2);
//        userDAO.save(user3);

        //System.out.println(userDAO.getAll());

        //Room room = new Room(1, 120.23, 0, 0, new Date(), null);

        //Hotel hotel1 = hotelDAO.findById(49);

        //System.out.println(hotel1);


        //room.setHotel(hotel1);
        //roomDAO.save(room);

        //System.out.println(roomDAO.getAll());
        //System.out.println(roomDAO.findById(87));

       // System.out.println(hotelDAO.findHotelByCity("Полтава"));


//        System.out.println(userDAO.getAll());
//        System.out.println(userDAO.findUserByName("Коля"));
//
        //System.out.println(roomDAO.getAll());


        //roomDAO.bookRoom(87, 81, 41);
        System.out.println(orderDAO.getAll());



    }
}
