package object; 

import helper.communication.*;
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

    public void instantiate() throws SQLException {
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

            for (int i = 0; i < 10; i++) {
                seaTrade.send("getinfo:harbour");
                String harbourName = seaTrade.receive();
                Harbor harbour = new Harbor().instantiate(harbourName);
                db.setHabor(harbour.name, harbour.coordinate);
                System.out.println("Server: " + harbourName);
            }
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

    private String genarateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
