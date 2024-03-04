package object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.*;

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
        String insertQuery = "INSERT IGNORE INTO Company (ID,CompanyName,Guthaben) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        if (!compnayId.equals("null")) {
            statement.setString(1, compnayId);
            statement.setString(2, companyName);
            statement.setString(3, deposit);
            statement.executeUpdate();
        }
    }
    
    public void clearTable(String table) throws SQLException {
        String insertStatement = "DELETE FROM " + table;
        PreparedStatement statement = connection.prepareStatement(insertStatement);

        statement.executeUpdate();
    }
    
    public void setShip(String shipId, String shipName, String companyID, String haborName) throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Schiffe (ID, SchiffName, CompanyID, HafenName) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        if (!shipId.equals("null")) {
            statement.setString(1, shipId);
            statement.setString(2, shipName);
            statement.setString(3, companyID);
            statement.setString(4, haborName);
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

    public void setCargo(String cargoId, int value, boolean IsAvailable, String source, String destination)
            throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Ladungen (ID, Wert, IsAvailablle, StartHafen, ZielHafen) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        if (!cargoId.equals("null")) {
            statement.setString(1, cargoId);
            statement.setInt(2, value);
            statement.setBoolean(3, IsAvailable);
            statement.setString(4, source);
            statement.setString(5, destination);
            statement.executeUpdate();
        }
    }

    public void removeCargo(String id) throws SQLException {
        String query = "DELETE FROM Ladungen WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setString(1, id);
        statement.executeUpdate();
    }

    public String getCompanys() throws SQLException {
        String shipsQuery = "SELECT * FROM Company";
        PreparedStatement statement = connection.prepareStatement(shipsQuery);
        ResultSet result = statement.executeQuery();

        StringBuilder output = new StringBuilder();

        while (result.next()) {
            String id = result.getString("id");
            String companyName = result.getString("companyname");
            int guthaben = result.getInt("guthaben");

            output.append("").append(id)
                  .append(",").append(companyName)
                  .append(",").append(guthaben)
                  .append("\n");
        }
        return output.toString();
    }

     public String getCargos() throws SQLException {
        String shipsQuery = "SELECT * FROM Ladungen WHERE isAvailablle=1";
        PreparedStatement statement = connection.prepareStatement(shipsQuery);
        ResultSet result = statement.executeQuery();

        StringBuilder output = new StringBuilder();

        while (result.next()) {
            String id = result.getString("ID");
            String wert = result.getString("Wert");
            String start = result.getString("Starthafen");
            String ziel = result.getString("Zielhafen");

            output.append("CARGO").append("|").append(id)
                  .append("|").append(start)
                  .append("|").append(ziel)
                  .append("|").append(wert)
                  .append("\n");
        }
        // System.out.println(output.toString());
        return output.toString();
    }

   
    public void getShips(String companyID) throws SQLException {
        String shipsQuery = "SELECT * FROM schiffe WHERE CompanyID = ?";
        PreparedStatement statement = connection.prepareStatement(shipsQuery);
        statement.setString(1, companyID);
        ResultSet result = statement.executeQuery();

        while (result.next()) {
            String id = result.getString("id");
            String shipName = result.getString("shipname");
            String company = result.getString("CompanyID");
            String hafenName = result.getString("hafenname");

            System.out.println(
                    "ID: " + id + ", Ship Name: " + shipName + ", Company ID: " + company + ", Cargo: " + hafenName);
        }
    }
}
