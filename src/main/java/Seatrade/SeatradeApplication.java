package Seatrade;

import java.sql.SQLException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Seatrade.object.Company;

@SpringBootApplication
public class SeatradeApplication {

	public static void main(String[] args) throws SQLException, InterruptedException {
		SpringApplication.run(SeatradeApplication.class, args);

		try {
			new Company("company").instantiate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
