package instance;

import helper.communication.*;

public class Ship {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new Client(8150, "localhost").run(); 
    }

}
