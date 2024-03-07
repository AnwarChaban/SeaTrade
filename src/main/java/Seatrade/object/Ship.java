package Seatrade.object; 

import Seatrade.helper.communication.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import Seatrade.sea.*;

// Ali hat die Klasse gemacht

public class Ship {
    String name;
    String company;
    Client toSeaTrade;
    Datenbank db;

    public Ship(String name, String company) {
        this.name = name;
        this.company = company;
        this.db = new Datenbank();
    }

    public void instantiate(String shipId) throws IOException, SQLException {
        Thread ship = new Thread() {
            public void run() {
                try {
                    Ship.this.connectToSeaTrade();
                    Ship.this.registerShip(shipId);

                    String cargo = Ship.this.db.getCargo();
                    while (!cargo.equals("")) {
                        Ship.this.handleCargo(cargo);
                        cargo = Ship.this.db.getCargo();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        ship.start();
    }

    private void connectToSeaTrade() throws IOException {
        this.toSeaTrade = new Client(8151, "localhost");
        new Thread(this.toSeaTrade).start();
    }

    /* 
     * save the Ship in the DB and register it at the seaTrade server
     */
    private void registerShip(String shipId) throws IOException, SQLException {
        String startHarbour = db.getRandomHarbour();
        this.db.setShip(shipId, this.name, this.company);
        this.toSeaTrade.send(String.format("launch:%s:%s:%s", Ship.this.company, startHarbour, Ship.this.name));
    }

    /*
     * load the cargo from the source harbour,
     * ... deliver it to the destination and collect the fee
     */
    private void handleCargo(String firstCargo) throws IOException, SQLException {
        /* 
         * everytime the Cargo string gets parsed
         * it generates a new random id for the cargo 
         * ... but we need the actual id to collect the cargo
         */
        String cargoId = firstCargo.split("\\|")[1];
        Cargo cargo = Cargo.parse(firstCargo);
        
        /*  
         *  set cargo as not avaiable inside the DB
         *  ... so other Ships won't drive there 
         *  ... because the cargo would already be gone 
         */
        db.setCargo(
            cargoId,
            cargo.getValue(),
            false,
            cargo.getSource(),
            cargo.getDestination()
        );

        processCargo(cargoId, cargo);
    }

    private void processCargo(String cargoId, Cargo cargo) throws IOException, SQLException {
        moveToSourceAndLoadCargo(cargoId, cargo);
        moveToDestinationAndUnloadCargo(cargo);
        collectFee(cargo);
    }
    
    private void moveToSourceAndLoadCargo(String cargoId, Cargo cargo) throws IOException {
        this.toSeaTrade.send("moveto:" + cargo.getSource());
        waitForArrival();
        this.toSeaTrade.send("loadcargo:" + cargoId);
    }
    
    private void moveToDestinationAndUnloadCargo(Cargo cargo) throws IOException {
        this.toSeaTrade.send("moveto:" + cargo.getDestination());
        waitForArrival();
        this.toSeaTrade.send("unloadcargo");
    }

    private void collectFee(Cargo cargo) throws IOException, SQLException {
        String cargoFee = toSeaTrade.receive().split(":")[1];
        String companyDeposit = db.getCompanyDeposit(this.company);
        int newDeposit = Integer.parseInt(companyDeposit) + Integer.parseInt(cargoFee);

        this.db.updateCompanyMoney(this.company, newDeposit);
    }

    private void waitForArrival() throws IOException {
        this.toSeaTrade.send("radarrequest");
        String arrived = this.toSeaTrade.receive();
        while (!arrived.startsWith("reached:")) {
            arrived = this.toSeaTrade.receive();
        }
    }
}