package object; 

import helper.communication.*;
import sea.Cargo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

// Author: Anwar Chaban
public class Company {
    String name;
    private String id;
    private String deposit;
    private Datenbank db;

    public Company(String name) {
        this.name = name;
        this.id = genarateId();
        this.db = new Datenbank();
    }

    public void instantiate() throws IOException, SQLException, InterruptedException {
        // connect to seaTrade server
        Client seaTrade = new Client(8150, "localhost");
        new Thread(seaTrade).run();
    
        String registerMessage = String.format("register:%s", this.name);
        seaTrade.send(registerMessage);
        this.deposit = seaTrade.receive().split(":")[2];

        setupDb(seaTrade);
        buyShips();
    }
    
    private void buyShips() throws IOException, SQLException, InterruptedException {
        int shipCost = 2000000;
        int i = 1;
        while (true) {
            int companyDeposit = Integer.parseInt(db.getCompanyDeposit(this.name));
            if (companyDeposit >= shipCost) {
                int newDeposit = companyDeposit - shipCost;
                // turn the number negative to subtract it from the deposit
                db.updateCompanyMoney(this.name, newDeposit);

                String shipName = "ship-" + i;
                String shipId = genarateId();
                new Ship(shipName, this.name).instantiate(shipId);
                Thread.sleep(30000); // wait 30 seconds until buying the next Ship
            }
        }
    }

    private void setupDb(Client seaTrade) throws IOException, SQLException, InterruptedException {
        setCompany();
        setHarbour(seaTrade);
        setCargo(seaTrade);
    }

    private void setCompany() throws SQLException {
        db.clearTable("Company");
        db.setCompany(this.id, this.name, this.deposit);
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
        db.clearTable("Ladungen");
        seaTrade.send("getinfo:cargo");
        String cargoName = seaTrade.receive();
        while (!cargoName.equals("endinfo")) {
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

    private String genarateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
