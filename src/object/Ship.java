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
        RadarField[] radarFields = radarScreen.getMeasures();

        // radarFields is a List containing information about each block around the ship
        // when using RadarField.parse() you can pass in an item out of this list...
        // ...it tells you wether the given Block is free or not
        System.out.println(Arrays.toString(radarFields));
        
        // map enum values to radarField values
        // rerun the loop if no free block was found ()
        for (int i = 0; i < radarFields.length; i += 2) {
              // find the first free block on the radar
            if (radarFields[i].getGround() == Ground.WASSER && !radarFields[i].isHasShip()) {
                System.out.println(radarFields[i].getGround());
            }
        }
        // for (RadarField radarField : radarFields) {
        //      // find the first free block on the radar
        //     if (radarField.getGround() == Ground.WASSER && !radarField.isHasShip()) {
        //         System.out.println(radarField.toString());
        //     }
        // }
        // System.out.println(directions); 
    }
}
