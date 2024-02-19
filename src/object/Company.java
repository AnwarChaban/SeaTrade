package object; 

import helper.communication.*;
import java.io.IOException;

public class Company {
    String name;
    ArrayList<Ship> shipList = new ArrayList<Ship>;

    public Company(String name) {
        this.name = name;
    }

    public void instantiate() {
        Client seaTrade = new Client(8150, "localhost");
        new Thread(seaTrade).run();
        

        Client ship = new Client(8150, "localhost");
        new Thread(ship).run();
        
        try {
            seaTrade.send(String.format("register:%s", this.name));
            System.out.println("Server: " + client.receive());
            
//            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Ship instantiateShip(String shipName) {
        Ship ship = new Ship(shipName, this.name).instantiate("plymouth");
        shipList.add(ship);
    }
}
