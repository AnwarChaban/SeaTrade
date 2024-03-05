package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.Desktop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.object.Company;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws SQLException, InterruptedException {
		SpringApplication.run(DemoApplication.class, args);

		
		new Company("test1s23").instantiate();
	
		System.out.println("New Company instance!");

	}

}
