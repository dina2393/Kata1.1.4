package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }



    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT PRIMARY KEY AUTO_INCREMENT, firstName VARCHAR(20), lastName VARCHAR(20), age TINYINT)")
            .addEntity(User.class)
            .executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            //проверка на null?
            session.getTransaction().commit();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            userList = session.createQuery("from User ").getResultList();
            session.getTransaction().commit();
        }
        for (User e: userList) {
            System.out.println(e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users")
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
