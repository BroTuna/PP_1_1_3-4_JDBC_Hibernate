package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbURL = "jdbc:mysql://localhost:3307/test";
    private static String dbUSERNAME = "root";
    private static String dbPASSWORD = "Shprota5885";

    public static Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbURL, dbUSERNAME, dbPASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Подключение установлено");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}