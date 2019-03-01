package repository;

import model.Hotel;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO implements DAO<Hotel> {
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        //singletion patern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }


    @Override
    public Hotel save(Hotel hotel) {
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(hotel);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save hotel is done");
        } catch (HibernateException e) {
            System.err.println("Save hotel is failed");
            System.out.println(e.getMessage());
            hotel = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return hotel;
    }

    @Override
    public Hotel delete(Hotel hotel) {

        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.delete(hotel);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete hotel is done");
        } catch (HibernateException e) {
            System.err.println("Delete hotel is failed");
            System.out.println(e.getMessage());
            hotel = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }


        return hotel;
    }

    @Override
    public Hotel update(Hotel hotel) {

        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.update(hotel);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update hotel is done");
        } catch (HibernateException e) {
            System.err.println("Update hotel is failed");
            System.out.println(e.getMessage());
            hotel = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return hotel;
    }

    @Override
    public Hotel findById(long id) {
        Session session = null;
        Transaction tr = null;
        Hotel resHotel = new Hotel();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            resHotel = session.get(Hotel.class, id);
            System.out.println("Hotel name = " + resHotel.getName());
            session.getTransaction().commit();
            System.out.println("find hotel by id is done");

        } catch (HibernateException e) {
            System.err.println("find hote by id is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resHotel;
    }


    public List<Hotel> findHotelByName(String name) {
        List<Hotel> resList = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Hotel where name=: name");
            query.setParameter("name", name);
            resList = query.list();
            session.getTransaction().commit();
            System.out.println("find hotel by name is done");

        } catch (HibernateException e) {
            System.err.println("find hotel by name is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resList;
    }



    public List<Hotel> findHotelByCity(String city) {
        List<Hotel> resList = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Hotel where city=: city");
            query.setParameter("city", city);
            resList = query.list();
            session.getTransaction().commit();
            System.out.println("find hotel by city is done");

        } catch (HibernateException e) {
            System.err.println("find hotel by city is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resList;
    }

}
