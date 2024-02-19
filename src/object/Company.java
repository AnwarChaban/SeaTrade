package object; 

import helper.communication.*;
import java.io.IOException;
import java.util.*;

public class Company {
    String name;
    ArrayList<Ship> shipList = new ArrayList<Ship>();

    public Company(String name) {
        this.name = name;
    }

    public void instantiate() {
        // connect to seaTrade server
        Client seaTrade = new Client(8150, "localhost");
        new Thread(seaTrade).run();
        

        try {
            seaTrade.send(String.format("register:%s", this.name));
            System.out.println("Server: " + seaTrade.receive());
            instantiateShip("ship1");
            seaTrade.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void instantiateShip(String shipName) {
        Ship ship = new Ship(shipName, this.name).instantiate("plymouth");
        shipList.add(ship);
    }
}
