package sql;

import java.sql.*;

public class SQLConnector {

    public static Connection getConnection() {
        Connection conn = null;
        String DB_URL = ""; //personal
        String USER_NAME = ""; //personal
        String PASSWORD = ""; //personal
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
