package com.example.demo.helper.communication;

import java.io.*;
import java.net.*;

class Server implements Runnable {
    private int port;

    public Server(int port) {
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
