package com.example.demo.object; 

import com.example.demo.helper.communication.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

import com.example.demo.sea.*;

// Ali hat die Klasse gemacht

public class Ship {
    String name;
    String company;
    String id;
    boolean isAvailable;
    Client toSeaTrade;
    private Datenbank db;

    public Ship(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public Ship instantiate(String harbour) {
        toSeaTrade = new Client(8151, "localhost");
        new Thread(toSeaTrade).run();
        
        try {
            toSeaTrade.send(String.format("launch:%s:%s:%s", this.company, harbour, this.name));
        
            getCargoList();
    //    String[] tokens = registerShip(harbour).split(":");
    //         // Position pos = Position.parse(tokens[1]);
    //         System.out.println(Arrays.toString(tokens));
            // setRadar();
            return this;
           // client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    String registerShip(String harbour) throws IOException  {
        toSeaTrade.send(String.format("launch:%s:%s:%s", this.company, harbour, this.name));
        db = new Datenbank();
        this.id = genarateId();
        try {
            db.setShip(this.id,this.name, this.company, harbour);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toSeaTrade.receive();
    }

    // get the List of cargos
    private void getCargoList() {
        Datenbank db = new Datenbank();
        try {
            // select a cargo out of this list
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
            System.out.println("UNLOAD CARGO: " + toSeaTrade.receive());
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
      private String genarateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    // private void setRadar() throws IOException {
    //     toSeaTrade.send("radarrequest");
    //     String[] tokens = toSeaTrade.receive().split(":");
    //     RadarScreen radarScreen = RadarScreen.parse(tokens[1]);
    //     RadarField[] radarFields = radarScreen.getMeasures();
    //
    //     // radarFields is a List containing information about each block around the ship
    //     // when using RadarField.parse() you can pass in an item out of this list...
    //     // ...it tells you wether the given Block is free or not
    //     System.out.println(Arrays.toString(radarFields));
    //     
    //     String[] directions = {"WEST", "NORTH", "EAST", "SOUTH"};
    //
    //     // map enum values to radarField values
    //     // rerun the loop if no free block was found ()
    //     for (int i = 0; i < radarFields.length; i += 2) {
    //           // find the first free block on the radar
    //         if (radarFields[i].getGround() == Ground.WASSER && !radarFields[i].isHasShip()) {
    //             System.out.println("Ground: " + radarFields[i].getGround());
    //             System.out.println("Direction: " + directions[i/2]);
    //             System.out.println("move:" + directions[i/2]);
    //             String foo = "moveto:brest";
    //             toSeaTrade.send(foo);
    //         }
    //     }
    //     // for (RadarField radarField : radarFields) {
    //     //      // find the first free block on the radar
    //     //     if (radarField.getGround() == Ground.WASSER && !radarField.isHasShip()) {
    //     //         System.out.println(radarField.toString());
    //     //     }
    //     // }
    //     // System.out.println(directions); 
    // }
}