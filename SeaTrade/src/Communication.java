import java.io.*;
import java.net.*;
 
public class Communication {
     
 
    static class Server implements Runnable {
        private int port;
        public static void main(String[] args) {
                
            // Start both client and server in separate threads
            new Thread(new Server(444)).start();
            new Thread(new Client(444, "localhost")).start();
        }
        Server(int port) {
            this.port = port;
        }
 
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Server started. Waiting for a client...");
 
                // Wait for client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");
 
                // Open streams for communication
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
 
                // Read messages from client and print them
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Client: " + message);
 
                    // Echo the message back to the client
                    out.println("Server: " + message);
                }
 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    static class Client implements Runnable {
        private int port;
        private String ip;
 
        Client(int port, String ip) {
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
}