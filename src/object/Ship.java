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

    public void instantiate() {
        Client client = new Client(8151, "localhost");
        new Thread(client).run();
        
        try {
            client.send("Hello, Server!");
            System.out.println("Server says: " + client.receive());

            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
