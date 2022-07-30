package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.StatementImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.internal.log.ConnectionAccessLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private List<User> users = new ArrayList<>();
    private static final Connection CONNECTION = Util.getConnection();

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS test (" +
            "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY ," +
            "name varchar(45) not null ," +
            "lastName varchar(45) not null ," +
            "age int not null)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS test";
    private static final String ALL = "SELECT * FROM test";
    private static final String DELETE = "DELETE FROM test WHERE id = ?";
    private static final String INSERT = "INSERT INTO test (name, lastName, age)" +
            " VALUES (?, ?, ?)";
    private static final String CLEAR = "TRUNCATE TABLE test";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(CREATE);
            System.out.println("Таблица создана");
            CONNECTION.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(DELETE_TABLE);
            System.out.println("Таблица удалена");
            CONNECTION.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(INSERT)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            CONNECTION.commit();
        } catch (SQLException e) {
            try {
                CONNECTION.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User с id = " + id + " удален из базы данных");
            CONNECTION.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(ALL);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
                System.out.println(users);
            }
            CONNECTION.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable () {
        try (Statement statement = CONNECTION.createStatement()) {
            statement.executeUpdate(CLEAR);
            System.out.println("Таблица очищена");
            CONNECTION.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
