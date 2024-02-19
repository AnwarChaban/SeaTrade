package object; 

import helper.communication.*;
import java.io.IOException;

public class Ship {
    String name;
    String company;

    public Ship(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public Ship instantiate(String harbour) {
        Client client = new Client(8151, "localhost");
        new Thread(client).run();
        
        try {
            client.send(String.format("launch:%s:%s:%s", this.company, harbour, this.name));
            System.out.println("Ship launched: " + client.receive());

            client.send("radarrequest");
            System.out.println("radar infos: " + client.receive());

            return this;
//            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
