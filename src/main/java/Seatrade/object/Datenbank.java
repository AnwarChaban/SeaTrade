package Seatrade.object;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// gemeinsam gemacht 
public class Datenbank {
    private Connection connection;

    public Datenbank() {
        connection = new SqlConnection().getConnection();
    }

    public void close() throws SQLException {
        connection.close();
    }

    // Anwar hat die die Methode erstellt
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

    // Anwar hat die die Methode erstellt
    public void setShip(String shipId, String shipName, String companyID) throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Schiffe (ID, SchiffName, CompanyID) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        if (!shipId.equals("null")) {
            statement.setString(1, shipId);
            statement.setString(2, shipName);
            statement.setString(3, companyID);
            statement.executeUpdate();
        }
    }

    // Anwar hat die die Methode erstellt
    public void setHabor(String haborName, String position) throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Hafen (HafenName, Koordinaten) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        if (!haborName.equals("null")) {
            statement.setString(1, haborName);
            statement.setString(2, position);
            statement.executeUpdate();
        }
    }

    // Anwar hat die die Methode erstellt
    public void setCargo(String cargoId, int value, boolean IsAvailable, String source, String destination)
            throws SQLException {
        String insertQuery = "INSERT IGNORE INTO Ladungen (ID, Wert, IsAvailable, StartHafen, ZielHafen) VALUES (?, ?, ?, ?, ?)";
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
    // Ali hat die die Methode erstellt
    public String getRandomHarbour() throws SQLException {
        String query = "SELECT HafenName FROM Hafen ORDER BY RAND() LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        StringBuilder output = new StringBuilder();

        while (result.next()) {
            output.append(result.getString("HafenName"));
        }

        return output.toString();
    }
    
    // Ali hat die die Methode erstellt
    public String getCompanyDeposit(String companyName) throws SQLException {
        String query = "SELECT Guthaben FROM Company WHERE companyName = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, companyName);
        ResultSet result = statement.executeQuery();
        StringBuilder output = new StringBuilder();

        while (result.next()) {
            output.append(result.getString("Guthaben"));
        }
        
        return output.toString();
    }

    // Ali hat die die Methode erstellt
    public void removeCargo(String id) throws SQLException {
        String query = "DELETE FROM Ladungen WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setString(1, id);
        statement.executeUpdate();
    }
    
    // Ali hat die die Methode erstellt
    public String getCargo() throws SQLException {
        String shipsQuery = "SELECT * FROM Ladungen WHERE IsAvailable=1 LIMIT 1";
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
        return output.toString();
    }

    // Anwar hat die die Methode erstellt
    public void updateCompanyMoney(String companyName, int money) throws SQLException {
        String query = "UPDATE Company SET Guthaben = ? WHERE CompanyName = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        
        statement.setInt(1, money);
        statement.setString(2, companyName);
        statement.executeUpdate();
    } 

       // Anwar hat die die Methode erstellt
       public void clearTable(String table) throws SQLException {
        foreignKeyCheck(0);
        String insertStatement = "TRUNCATE " + table;
        PreparedStatement statement = connection.prepareStatement(insertStatement);

        statement.executeUpdate();
        foreignKeyCheck(1);
    }    

    private void foreignKeyCheck(int number) throws SQLException {
        String insertStatement = "SET FOREIGN_KEY_CHECKS=" + number;
        PreparedStatement statement = connection.prepareStatement(insertStatement);
        statement.executeUpdate();
    }

    // Miro hat die die Methode erstellt
    public String getCargos() throws SQLException {
        String shipsQuery = "SELECT * FROM Ladungen WHERE IsAvailable=1";
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

     // Miro hat die die Methode erstellt
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
                    .append(";");
        }
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
        }
        return output.toString();
    }

    // Miro hat die die Methode erstellt
    public String getShips() throws SQLException {
        String shipsQuery = "SELECT * FROM schiffe";
        PreparedStatement statement = connection.prepareStatement(shipsQuery);
        ResultSet result = statement.executeQuery();
        StringBuilder output = new StringBuilder();

        while (result.next()) {
            String id = result.getString("id");
            String shipName = result.getString("schiffName");
            String hafenName = result.getString("hafenName");

            output.append("").append(id)
                    .append(",").append(shipName)
                    .append(",").append(hafenName)
                    .append(";");
        }
        if (output.length() > 0) {
            output.deleteCharAt(output.length() - 1);
        }
        return output.toString();
    }
}