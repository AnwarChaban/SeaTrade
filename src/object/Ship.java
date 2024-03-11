package object; 

import helper.communication.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import sea.*;

// Author: Ali Al-Wazny
public class Ship {
    String name;
    String companyName;
    String companyId;
    Client toSeaTrade;
    Datenbank db;

    public Ship(String name, String companyName, String companyId) {
        this.name = name;
        this.companyName = companyName;
        this.companyId = companyId;
        this.db = Datenbank.getInstance();
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
        this.db.setShip(shipId, this.name, this.companyId);
        this.toSeaTrade.send(String.format("launch:%s:%s:%s", Ship.this.companyName, startHarbour, Ship.this.name));
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
        
        processCargo(cargoId, cargo);
    }

    private void processCargo(String cargoId, Cargo cargo) throws IOException, SQLException {
        moveToSourceAndLoadCargo(cargoId, cargo);
        moveToDestinationAndUnloadCargo(cargoId, cargo);
        collectFee(cargo);
    }
 
    /*  
     *  set cargo as not avaiable inside the DB
     *  ... so other Ships won't drive there 
     *  ... because the cargo would already be gone 
     */
    private void moveToSourceAndLoadCargo(String cargoId, Cargo cargo) throws IOException, SQLException {
        this.db.reserveCargo(cargoId);
        this.toSeaTrade.send("moveto:" + cargo.getSource());
        waitForArrival();
        this.toSeaTrade.send("loadcargo:" + cargoId);
    }
    
    private void moveToDestinationAndUnloadCargo(String cargoId, Cargo cargo) throws IOException, SQLException {
        this.toSeaTrade.send("moveto:" + cargo.getDestination());
        waitForArrival();
        this.toSeaTrade.send("unloadcargo");
        this.db.removeCargo(cargoId);
    }

    private void collectFee(Cargo cargo) throws IOException, SQLException {
        String cargoFee = toSeaTrade.receive().split(":")[1];
        String companyDeposit = db.getCompanyDeposit(this.companyId);
        int newDeposit = Integer.parseInt(companyDeposit) + Integer.parseInt(cargoFee);

        this.db.updateCompanyMoney(this.companyName, newDeposit);
    }

    private void waitForArrival() throws IOException {
        this.toSeaTrade.send("radarrequest");
        String arrived = this.toSeaTrade.receive();
        while (!arrived.startsWith("reached:")) {
            arrived = this.toSeaTrade.receive();
        }
    }
}
