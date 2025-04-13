package com.tma.training;

import java.io.*;
import java.net.*;

public class BlockingMultiThreadedEchoServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Blocking Echo Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start(); // Each client gets a new thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) { // BLOCKING
                System.out.println("Received: " + line);
                writer.write("Echo: " + line + "\n");
                writer.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
