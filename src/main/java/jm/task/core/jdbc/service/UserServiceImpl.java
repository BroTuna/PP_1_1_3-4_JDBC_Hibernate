package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao servise = new UserDaoJDBCImpl();
    public void createUsersTable() {
        servise.createUsersTable();
    }

    public void dropUsersTable() {
        servise.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        servise.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        servise.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return servise.getAllUsers();
    }

    public void cleanUsersTable() {
        servise.cleanUsersTable();
    }
}
