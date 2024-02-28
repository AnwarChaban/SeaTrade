package object; 

import helper.communication.*;
import java.io.IOException;
import java.util.*;
import sea.*;

public class Ship {
    String name;
    String company;
    Client toSeaTrade;

    public Ship(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public Ship instantiate(String harbour) {
        toSeaTrade = new Client(8151, "localhost");
        new Thread(toSeaTrade).run();
        
        try {
            String[] tokens = registerShip(harbour).split(":");
            // Position pos = Position.parse(tokens[1]);
            System.out.println(Arrays.toString(tokens));
            setRadar();

            return this;
            // toSeaTrade.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private String registerShip(String harbour) throws IOException {
        toSeaTrade.send(String.format("launch:%s:%s:%s", this.company, harbour, this.name));

        return toSeaTrade.receive();
    }

    private void setRadar() throws IOException {
        toSeaTrade.send("radarrequest");
        String[] tokens = toSeaTrade.receive().split(":");
        RadarScreen radarScreen = RadarScreen.parse(tokens[1]);
        RadarField[] radarField = radarScreen.getMeasures();

        // radarFields is a List containing information about each block around the ship
        // when using RadarField.parse() you can pass in an item out of this list...
        // ...it tells you wether the given Block is free or not
        System.out.println(Arrays.toString(radarField));
    }
}