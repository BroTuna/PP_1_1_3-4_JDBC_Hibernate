package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();

        userDao.saveUser("Филипп", "Киркоров", (byte) 13);
        userDao.saveUser("Герман", "Севастьянов", (byte) 25);
        userDao.saveUser("Раб", "Божий", (byte) 33);
        userDao.saveUser("Вовкин", "Дед", (byte) 69);
        userDao.getAllUsers();
        userDao.removeUserById(1);
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
