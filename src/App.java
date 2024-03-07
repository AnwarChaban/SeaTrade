import java.sql.SQLException;

import object.*;

public class App {
    public static void main(String[] args) throws Exception {
        try {
            new Company("company").instantiate();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }
}
