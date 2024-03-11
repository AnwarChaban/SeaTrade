package object;

import java.util.*;
import sea.*;

public class Harbor {
     String name;
     String coordinate;

     public Harbor instantiate(String name) {
         String[] haborName = name.split(":");
         this.name = haborName[2];
         Position position = Position.parse(haborName[1]);
        this.coordinate = position.getX() + "," + position.getY();
        return this;
    }
}