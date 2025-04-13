package com.tma.training;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NonBlockingEchoServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Selector selector = Selector.open();
             ServerSocketChannel serverChannel = ServerSocketChannel.open()) {

            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false); // non-blocking mode
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Non-blocking Echo Server is running on port " + PORT);

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (true) {
                selector.select(); // blocks until at least one channel is ready

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();

                    if (key.isAcceptable()) {
                        handleAccept(serverChannel, selector);
                    }

                    if (key.isReadable()) {
                        handleRead(key, buffer);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleAccept(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
        }
    }

    private static void handleRead(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        buffer.clear();

        int bytesRead = clientChannel.read(buffer); // non-blocking read
        if (bytesRead == -1) {
            System.out.println("Client disconnected: " + clientChannel.getRemoteAddress());
            clientChannel.close();
            key.cancel();
            return;
        }

        buffer.flip();
        String received = StandardCharsets.UTF_8.decode(buffer).toString().trim();

        System.out.println("Received: " + received);

        String response = "Echo: " + received + "\n";
        ByteBuffer responseBuffer = StandardCharsets.UTF_8.encode(response);
        clientChannel.write(responseBuffer);
    }
}
