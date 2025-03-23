package se.lexicon.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/todoit";
    public static final String UN = "root";
    public static final String PW = "1234";

    public static  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UN, PW);
    }

}
