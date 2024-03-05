package object; 

import helper.communication.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import sea.*;

public class Ship {
    String name;
    String company;
    boolean isAvailable;
    Client toSeaTrade;
    Datenbank db;

    public Ship(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public Ship instantiate(String startHarbour) {
        toSeaTrade = new Client(8151, "localhost");
        new Thread(toSeaTrade).run();
        db = new Datenbank();
        try {
            toSeaTrade.send(String.format("launch:%s:%s:%s", this.company, startHarbour, this.name));

            while (true) {
                getRandomCargo();
            }
           // client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return null;
    }
    
    // get the List of cargos
    private void getRandomCargo() {
        try {
            // select a cargo out of this list
            // TODO check if there is any cargo left
            String firstCargo = db.getCargos().split("\n")[0];
            // everytime the Cargo string gets parsed
            // it generates a new random id
            Cargo cargo = Cargo.parse(firstCargo);
            String cargoId = firstCargo.split("\\|")[1];
            // set cargo as not avaiable inside the DB
            db.setCargo(cargoId,
                        cargo.getValue(),
                        false,
                        cargo.getSource(),
                        cargo.getDestination());

            // set the availability of the ship to false
            isAvailable = false;
            // move the the harbour where the cargo is
            toSeaTrade.send("moveto:" + cargo.getSource());
            
            // wait until the Ship arrives at the destination
            waitForArrival();
            // collect the cargo 
            toSeaTrade.send("loadcargo:" + cargoId);
            System.out.println("LOAD CARGO: " + toSeaTrade.receive());
            // remove the cargo from the DB
            db.removeCargo(cargoId);
            // move the collected cargo to the destination
            toSeaTrade.send("moveto:" + cargo.getDestination());

            // collect the money when the Ship arrives at the destination
            waitForArrival();
            toSeaTrade.send("unloadcargo");

            String cargoFee = toSeaTrade.receive().split(":")[1];
            System.out.println("CARGO FEE: " + cargoFee);
            isAvailable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForArrival() {
        try {
            toSeaTrade.send("radarrequest");
            String arrived = toSeaTrade.receive();
            while (!arrived.startsWith("reached:")) {
                arrived = toSeaTrade.receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
