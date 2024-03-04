package com.example.demo.helper.communication;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/greeting")
    @CrossOrigin
    public String index() {
        System.out.println("Hello World!");
        return "Greetings from Spring Boot!";
    }

}