package repository;

import model.Hotel;
import model.User;
import model.UserType;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    public static User logenedUser = new User();
    private static SessionFactory sessionFactory;


    public static SessionFactory createSessionFactory() {
        //singletion patern
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static User getLogenedUser() {
        return logenedUser;
    }

    public static boolean logonWasSuccesful() {
        if (logenedUser.getUsername() != null)
            return true;
        return false;
    }

    public static boolean logenedUserHasAdminPermit() {
        if (logenedUser == null) return false;
        if (logenedUser.getUserType() == UserType.ADMIN)
            return true;
        return false;
    }

    public User registerUser(User user) throws Exception {
        return save(user);
    }

    public List<User> findUserByName(String userName) {

        List<User> resList = new ArrayList();
        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from User where username=:name");
            query.setParameter("name", userName);
            resList = query.list();
            resList.get(0).toString();
            session.getTransaction().commit();
            System.out.println("find user by name is done");
        } catch (HibernateException e) {
            System.err.println("find user by name is faild");
            System.out.println(e.getMessage());
            if (tr != null) {
                tr.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resList.isEmpty() ? null : resList;
    }


    @Override
    public User save(User user) {


        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //action
            session.save(user);

            //close session/tr
            session.getTransaction().commit();
            System.out.println("Save user is done");
        } catch (HibernateException e) {
            System.err.println("Save user is failed");
            System.out.println(e.getMessage());
            user = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return user;

    }

    @Override
    public User delete(User user) {
        Session session = null;
        Transaction tr = null;
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            //user.setOrders(null);
            //user.setOrders(null);
            session.delete(user);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Delete user is done");
        } catch (HibernateException e) {
            System.err.println("Delete user is failed");
            System.out.println(e.getMessage());
            user = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }

        return user;
    }

    @Override
    public User update(User user) {

        Session session = null;
        Transaction tr = null;

        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();


            session.update(user);
            //close session/tr
            session.getTransaction().commit();
            System.out.println("Update user is done");
        } catch (HibernateException e) {
            System.err.println("Update user is failed");
            System.out.println(e.getMessage());
            user = null;
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return user;

    }

    @Override
    public User findById(long id) {
        Session session = null;
        Transaction tr = null;

        User resUser = new User();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();

            resUser = session.get(User.class, id);

            //resUser.toString(); //Роблю для того щоб ініціалізувати PROXY інакше видає Exception

            session.getTransaction().commit();
            System.out.println("find user by id is done");

        } catch (HibernateException e) {
            System.err.println("find user by id is faild");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return resUser;
    }


    public List<User> getAll() {
        Session session = null;
        Transaction tr = null;
        List<User> resUsers = new ArrayList<>();
        try {
            session = createSessionFactory().openSession();
            tr = session.getTransaction();
            tr.begin();
            Query query = session.createQuery("from User");
            resUsers = query.list();
            resUsers.toString();
            session.getTransaction().commit();
            System.out.println("get all user done");
        } catch (HibernateException e) {
            System.out.println("get all user field");
            System.out.println(e.getMessage());
            if (tr != null)
                tr.rollback();

        } finally {
            if (session != null)
                session.close();
        }
        return resUsers;
    }


    public void login(String userName, String password) throws Exception {
        List<User> users = getAll();

        for (User element : users) {
            if (element.getUsername().equals(userName) &&
                    element.getPassword().equals(password)) {
                logenedUser = element;
                break;
            }
        }
        if (logenedUser.getUsername() != null) {
            System.out.println("Login user " + logenedUser.getUsername() + " are sucses");
        } else {
            throw new Exception("Login: User is not exist");
        }
    }

    public void logout() {
        logenedUser = null;
    }

}
