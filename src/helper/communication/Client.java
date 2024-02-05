package helper.communication;

import java.io.*;
import java.net.*;
 
public class Client implements Runnable {
    private int port;
    private String ip;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void run() {
        try {
            // Connect to the server
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to server.");

            // Open streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send messages to the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = reader.readLine()) != null) {
                out.println(message);

                // Receive and print response from server
                System.out.println("Server: " + in.readLine());
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
