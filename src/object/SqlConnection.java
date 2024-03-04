package object;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private String url = "jdbc:mysql://localhost:3306/seatrade";
    private String username = "lalwazny";
    private String password = "password";

    public java.sql.Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
            // Perform database operations here
            // ...
            return connection;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found for mysql Driver probably?");
            e.printStackTrace();
            return null;
        }
    }
}
