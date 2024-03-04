import java.io.File;
import java.io.IOException;

import object.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import object.Company;

public class App {
    public static void main(String[] args) throws Exception {

  
                File htmlFile = new File("src\\object\\s.html");
                try {
                    // open the default web browser for this HTML file
                    new Company("test1").instantiate();
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } catch (IOException e) {
                    // Log an error message if the file can't be opened
                    System.out.println("An error occurred while trying to open the HTML file: " + e.getMessage());
                }
                System.out.println("New Company instance!");
            

        // new Ship("test2", "test").instantiate();
        // System.out.println("New Ship instance!");
    }
}
