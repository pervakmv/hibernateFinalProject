package repository;

import model.Hotel;
import model.Order;
import model.Room;
import model.User;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import model.Filter;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoomDAO implements DAO<Room> {
    private static SessionFactory sessionFactory;

    HotelDAO hotelDAO = new HotelDAO();
    OrderDAO orderDAO = new OrderDAO();
    UserDAO userDAO = new UserDAO();

    public static SessionFactory createSessionFactory() {
        //singletion patern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    @Override
    public Room save(Room Room) {
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(Room);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save Room is done");
        } catch (HibernateException e) {
            System.err.println("Save Room is failed");
            System.out.println(e.getMessage());
            Room = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return Room;
    }

    @Override
    public Room delete(Room room) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            room.setHotel(null);
            session.delete(room);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete Room is done");
        } catch (HibernateException e) {
            System.err.println("Delete Room is failed");
            System.out.println(e.getMessage());
            room = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }


        return room;
    }

    @Override
    public Room update(Room room) {

        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();


            session.update(room);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update Room is done");
        } catch (HibernateException e) {
            System.err.println("Update Room is failed");
            System.out.println(e.getMessage());
            room = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return room;
    }

    @Override
    public Room findById(long id) {
        Session session = null;
        Transaction tr = null;

        Room resRoom = new Room();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            resRoom = session.get(Room.class, id);

            //resRoom.toString(); //Роблю для того щоб ініціалізувати PROXY інакше видає Exception

            session.getTransaction().commit();
            System.out.println("find Room by id is done");

        } catch (HibernateException e) {
            System.err.println("find hote by id is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return resRoom;
    }

    public List<Room> getAll() {
        Session session = null;
        Transaction tr = null;
        List<Room> resRooms = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Room");
            resRooms = query.list();


            session.getTransaction().commit();
            System.out.println("get all room done");
        } catch (HibernateException e) {
            System.out.println("get all room field");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return resRooms;
    }

    public void bookRoom(long roomId, long userId, long hotelId) throws Exception {
        //наличие отеля
        Hotel hotel = hotelDAO.findById(hotelId);

        if (hotel == null)
            throw new Exception("bookRoom: There is hotel with id: " + hotelId + " is not exist");
        //Наличие зарегистрированного пользователя
        User user = userDAO.findById(userId);

        if (user == null)
            throw new Exception("bookRoom: There is user with id: " + userId + " is not exist");

        //Проверяем наличие комнаты с таки ID
        Room room = findById(roomId);

        if (room.getHotel() != null) {
            if (room.getHotel().getId() != hotelId) {
                throw new Exception("bookRoom: Hotel " + hotel.getName() + " doesn't have room with id: " + roomId);
            }
        }

        int amountOfDays = 2;
        Date dateFrom = new Date();
        Date dateTo = new Date();
        dateTo.setDate(dateFrom.getDate() + amountOfDays);
        double moneyPaid = (double) amountOfDays * room.getPrice();
        Order order = new Order();
        order.setUser(user);
        order.setRoom(room);
        order.setDateFrom(dateFrom);
        order.setDateTo(dateTo);
        order.setMoneyPaid(moneyPaid);

        orderDAO.save(order);
    }

    public void cancelReservation(long roomId, long userId) throws Exception {
        boolean thereIsSuchAnElement = false;
        List<Order> orders = orderDAO.findByUserId(userId);
        if (orders == null)
            throw new Exception("This order is missing");
        for (Order element : orders) {
            if (element.getRoom().getId() == roomId) {
                orderDAO.delete(element);
                System.out.println("Order with roomId " + roomId + "for user id:" + userId + "has been delete");
            }
        }
    }

    public List<Room> findRooms(Filter filter) throws Exception {
        List<Room> res = new ArrayList<>();
        List<Room> rooms = getAll();

        for (Room rm : rooms) {
            if (rm.equalsByFilter(filter)) {
                res.add(rm);
            }
        }
        return res;
    }
}
