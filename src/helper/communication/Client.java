package helper.communication;

import java.io.*;
import java.net.*;
 
public class Client implements Runnable {
    private int port;
    private String ip;

    // dependencies
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void run() throws NullPointerException {
        try {
            // Connect to the server
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        socket.close();
    }

    public void send(String message) throws IOException {
        if (!message.equals("")) {
            out.println(message);
        }
    }

    public String receive() throws IOException {
        return in.readLine();
    }
}
