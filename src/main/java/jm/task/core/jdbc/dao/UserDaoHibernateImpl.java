package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();
    private static List<User> users;
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS user (" +
            "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
            "name varchar(45) not null ," +
            "lastName varchar(45) not null ," +
            "age int not null)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS user";
    private static final String CLEAR = "TRUNCATE TABLE user";

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try(Session session = factory.openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(CREATE)
                        .executeUpdate();
                session.getTransaction().commit();
                System.out.println("Таблица с названием \"user\" успешно создана");
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.getStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = factory.openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(DELETE_TABLE)
                        .executeUpdate();
                System.out.println("Таблица с именем \"user\" удалена");
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.getStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
                System.out.println("User с именем " + name + " успешно добавлен в базу данных");
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.getStackTrace();
            }
        }
    }

    @Override
    public void removeUserById ( long id) {
        try (Session session = factory.openSession()) {
            try {
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                session.getTransaction().commit();
                System.out.println("User с id = " + id + " успешно удален из базы данных");
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.getStackTrace();
            }
        }
    }
    @Override
    public List<User> getAllUsers () {
        try (Session session = factory.openSession()) {
           return session.createQuery("from User ").getResultList();
        }
    }
    @Override
    public void cleanUsersTable () {
        try(Session session = factory.openSession()) {
            try {
                session.beginTransaction();
                session.createSQLQuery(CLEAR)
                        .executeUpdate();
                session.getTransaction().commit();
                System.out.println("Таблица очищена");
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.getStackTrace();
            }
        }
    }
}
