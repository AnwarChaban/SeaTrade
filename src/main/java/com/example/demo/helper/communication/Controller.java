package com.example.demo.helper.communication;

import java.sql.SQLException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.object.Company;
import com.example.demo.object.Datenbank;

@RestController
public class Controller {

    @GetMapping("/greeting")
    @CrossOrigin
    public String index() {
        System.out.println("Hello World!");
        return "Greetings from Spring Boot!";
    }
    @GetMapping("/j")
    @CrossOrigin
    public String getCompanys() throws SQLException {
        Datenbank db = new Datenbank();
        return db.getCompanys();
    }
    // gwt ships
    @GetMapping("/s")
    @CrossOrigin
    public String getShips(String companyId) throws SQLException {
        Datenbank db = new Datenbank();
        return db.getShips(companyId);
    }
}