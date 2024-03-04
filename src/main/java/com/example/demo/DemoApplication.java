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

		File htmlFile = new File("src\\main\\resources\\s.html");
		if (htmlFile.exists()) {
			System.out.println("File exists");
		} else {
			System.out.println("File does not exist");
		}
		try {
			// open the default web browser for this HTML file
			new Company("test123").instantiate();
			// Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (Exception e) {
			// Log an error message if the file can't be opened
			System.out.println("An error occurred while trying to open the HTML file: " + e.getMessage());
		}
		System.out.println("New Company instance!");
	}

}
