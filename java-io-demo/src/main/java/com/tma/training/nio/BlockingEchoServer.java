package com.tma.training.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class BlockingEchoServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            serverChannel.bind(new InetSocketAddress(PORT));
            System.out.println("Blocking Echo Server is running on port " + PORT);

            while (true) {
                // Accept blocks until a client connects
                SocketChannel clientChannel = serverChannel.accept();
                System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
                handleClient(clientChannel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(SocketChannel clientChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try (SocketChannel channel = clientChannel) {
            while (true) {
                buffer.clear();
                int bytesRead = channel.read(buffer); // blocking read

                if (bytesRead == -1) {
                    System.out.println("Client disconnected: " + channel.getRemoteAddress());
                    break;
                }

                buffer.flip();
                String received = StandardCharsets.UTF_8.decode(buffer).toString().trim();
                System.out.println("Received: " + received);

                String response = "Echo: " + received + "\n";
                ByteBuffer responseBuffer = StandardCharsets.UTF_8.encode(response);
                channel.write(responseBuffer); // blocking write
            }
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }
}
