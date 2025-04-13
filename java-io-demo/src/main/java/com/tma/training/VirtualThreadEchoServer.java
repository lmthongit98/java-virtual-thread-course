package com.tma.training;

import java.io.*;
import java.net.*;

public class VirtualThreadEchoServer {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Virtual Thread Echo Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread.startVirtualThread(() -> handleClient(clientSocket));
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Received: " + line);
                String response = "Echo: " + line + "\n";
                writer.write(response);
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
