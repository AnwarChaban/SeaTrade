package object; 

import helper.communication.*;
import java.io.IOException;

public class Company {
    String name;
    String harbour;
    Client client = new Client(8150, "localhost");

    public Company(String name) {
        this.name = name;
    }

    public void instantiate() {
        new Thread(client).run();

        try {
            client.send(String.format("register:%s", this.name));
            System.out.println("Server: " + client.receive());
            getInfo();
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void getInfo() {
        try {
            client.send("getinfo:harbour");
            System.out.println("Server: " + client.receive());
            String deposit = client.receive();
            String deposits[] = deposit.split(":");
            System.out.println("Server: " + deposits[1]);
            
            client.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
