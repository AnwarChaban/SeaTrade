package Seatrade;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Seatrade.object.Company;

@SpringBootApplication
public class SeatradeApplication {

	public static void main(String[] args) throws SQLException, InterruptedException {
		SpringApplication.run(SeatradeApplication.class, args);

		
		new Company("test1s23").instantiate();
	
		System.out.println("New Company instance!");

	}

}
