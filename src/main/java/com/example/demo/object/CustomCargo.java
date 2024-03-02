package com.example.demo.object;

import com.example.demo.sea.Cargo;

public class CustomCargo {
    private String id;
    int value;
    String source;
    String destination;



    public CustomCargo instantiate(String name) {
        String[] haborName = name.split(":");
        Cargo cargo = Cargo.parse(haborName[1]);
        this.id = Integer.toString(cargo.getId());
        this.value = cargo.getValue();
        this.source = cargo.getSource();
        this.destination = cargo.getDestination();
        return this;
    }
   
    public String getId() {
        return id;
    }
}
