package object;

import java.util.*;

import sea.Position;

public class Harbor {
     String id;
     String name;
     String coordinate;


     public Harbor instantiate(String name) {
        Position position = Position.parse(name);
        this.id = genarateId();
        String[] haborName = name.split(":");
        this.name = haborName[2];
        this.coordinate = position.getX() + "," + position.getY();
        return this;
    }


    

    private String genarateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
