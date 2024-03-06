package com.example.demo.helper.communication;

import java.sql.SQLException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.object.Company;
import com.example.demo.object.Datenbank;


// Anwar hat die Klasse gemacht
@RestController
public class Controller {

    @GetMapping("/greeting")
    @CrossOrigin
    public String index() {
        System.out.println("Hello World!");
        return "Greetings from Spring Boot!";
    }
    @GetMapping("/getCompany")
    @CrossOrigin
    public String getCompanys() throws SQLException {
        Datenbank db = new Datenbank();
        return db.getCompanys();
    }
    @GetMapping("/login")
    @CrossOrigin
    @ResponseBody
    public String getCompanys(@RequestParam String companyName) throws SQLException, InterruptedException {

        new Company(companyName).instantiate();
        return companyName;
    }
    @GetMapping("/getShips")
    @CrossOrigin
    @ResponseBody
    public String getShips() throws SQLException, InterruptedException {

        Datenbank db = new Datenbank();
        return db.getShips();
    }
}