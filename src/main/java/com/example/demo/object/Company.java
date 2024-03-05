package com.example.demo.object; 

import com.example.demo.helper.communication.*;
import com.example.demo.sea.Cargo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Company {
    String name;
    private String id;
    private int deposit;
    private Datenbank db;
    ArrayList<Ship> shipList = new ArrayList<Ship>();

    public Company(String name) {
        this.name = name;
    }

    public void instantiate() throws SQLException, InterruptedException {
        // connect to seaTrade server
        Client seaTrade = new Client(8150, "localhost");
        db = new Datenbank();
        new Thread(seaTrade).run();
        

        try {
            seaTrade.send(String.format("register:%s", this.name));
            String deposit = seaTrade.receive();
            String deposits[] = deposit.split(":");
            this.id = genarateId();
            this.deposit = Integer.parseInt(deposits[2]); // Convert string to int
            db.setCompany(this.id, this.name, Integer.toString(this.deposit));

            setHarbour(seaTrade);
            setCargo(seaTrade);
            
            instantiateShip("ship1");
            seaTrade.stop();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHarbour(Client seaTrade) throws IOException, SQLException, InterruptedException {
        String harbourName = "";
        seaTrade.send("getinfo:harbour");
        while (!harbourName.equals("endinfo")) {
            harbourName = seaTrade.receive();
            if (!harbourName.equals("endinfo")) {
                Harbor harbour = new Harbor().instantiate(harbourName);
                db.setHabor(harbour.name, harbour.coordinate);
            }
            System.out.println("Server: " + harbourName);
        }
    }

    private void setCargo(Client seaTrade) throws IOException, SQLException, InterruptedException {
        // to avoid duplicate values from previous tries
        String cargoName = "";
        db.clearTable("Ladungen");
        seaTrade.send("getinfo:cargo");
        while (!cargoName.equals("endinfo")) {
            cargoName = seaTrade.receive();
            Cargo cargo = Cargo.parse(cargoName.split(":")[1]);
            // everytime the cargo string is parsed
            // ... the id of the cargo gets randommised
            db.setCargo(cargoName.split("\\|")[1],
                        cargo.getValue(), 
                        true,
                        cargo.getSource(),
                        cargo.getDestination());

            cargoName = seaTrade.receive();
        }
    }
    
    public void addShip(String shipName) {
        instantiateShip(shipName);
    }

    public Ship getShips(String companyName) throws SQLException {
        db = new Datenbank();
        db.getCompanys();
        return null;
    }

    private void instantiateShip(String shipName) {
        Ship ship = new Ship(shipName, this.name).instantiate("plymouth");
        shipList.add(ship);
    }


    private String genarateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}