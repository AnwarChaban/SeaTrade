package object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Datenbank {
    private Connection connection;
    private String query;

    public Datenbank() {
        // TODO: Implement constructor
        connection = new SqlConnection().getConnection();
    }

    public void execQuery() {
        // TODO: Implement execQuery method
        //
    }

    public void execStatement() {
        // TODO: Implement execStatement method
    }

    public void setCompany(String compnayId, String companyName, String deposit) throws SQLException {
        String insertQuery = "INSERT IGNORE INTO company (ID,CompanyName,Guthaben) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);
            

        if (!compnayId.equals("null")) {
            statement.setString(1, compnayId);
            statement.setString(2, companyName);
            statement.setString(3, deposit);
            statement.executeUpdate();
        }
    }
    public void setHabor(String haborName, String position) throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Hafen (HafenName, Koordinaten) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);
            

        if (!haborName.equals("null")) {
           statement.setString(1, haborName);
            statement.setString(2, position);
            statement.executeUpdate();
        }
    }
}
 