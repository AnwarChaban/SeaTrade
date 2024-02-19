package object; 

import helper.communication.*;
import java.io.IOException;

public class Company {
    String name;

    public Company(String name) {
        this.name = name;
    }

    public void instantiate() {
        Client client = new Client(8150, "localhost");
        new Thread(client).run();
        
        try {
            client.send(String.format("register:%s", this.name));
            System.out.println("Server: " + client.receive());

            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
