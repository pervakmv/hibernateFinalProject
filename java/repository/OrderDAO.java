package repository;

import model.Hotel;
import model.Order;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DAO<Order> {
    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        //singletion patern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    @Override
    public Order save(Order order) {
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            //action
            session.save(order);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save order is done");
        } catch (HibernateException e) {
            System.err.println("Save order is failed");
            System.out.println(e.getMessage());
            order = null;
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return order;
    }

    @Override
    public Order delete(Order order) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            order.setRoom(null);
            order.setUser(null);
            session.delete(order);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete order is done");
        } catch (HibernateException e) {
            System.err.println("Delete order is failed");
            System.out.println(e.getMessage());
            order = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return order;
    }

    @Override
    public Order update(Order order) {
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            session.update(order);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update Room is done");
        } catch (HibernateException e) {
            System.err.println("Update Room is failed");
            System.out.println(e.getMessage());
            order = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return order;

    }

    @Override
    public Order findById(long id) {
        Session session = null;
        Transaction tr = null;

        Order resOrder = new Order();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            resOrder = session.get(Order.class, id);

            resOrder.toString(); //Роблю для того щоб ініціалізувати PROXY інакше видає Exception

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
        return resOrder;
    }


    public List<Order> getAll() {
        Session session = null;
        Transaction tr = null;
        List<Order> resOrders = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from Order");
            resOrders = query.list();
            resOrders.toString();
            session.getTransaction().commit();
            System.out.println("get all order done");
        } catch (HibernateException e) {
            System.out.println("get all order field");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null)
                session.close();
        }
        return resOrders;
    }

    public List<Order> findByUserId(long userId) {
        List<Order> resList = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            org.hibernate.Query query = session.createQuery("from Order where user_id=:userId");
            query.setParameter("user_id", userId);

            resList = query.list();
            session.getTransaction().commit();
            System.out.println("find order by roomId and userId is done");

        } catch (HibernateException e) {
            System.err.println("find order by roomId and userId is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resList.isEmpty() ? null : resList;
    }
}
