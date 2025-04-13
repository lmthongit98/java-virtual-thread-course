/*
 * You might wonder why a non-blocking read is still necessary even after select() indicates that a socket is ready.
 * The reason is that select() only tells you that the socket was ready at the time of the check.
 * However, by the time you call read(), the data might have already been consumed by another thread or process,
 * resulting in a blocking call if the socket is no longer ready — this is a classic race condition.
 *
 * Calling a blocking read() in this case defeats the purpose of using I/O multiplexing,
 * which is intended to avoid blocking and allow efficient handling of multiple connections.
 *
 * Timeline example:
 * [T1] select() says socket A is readable
 * [T2] another thread reads from socket A
 * [T3] current thread calls blocking read() on socket A => BLOCKS!
 */

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
            serverChannel.configureBlocking(false); // setup non-blocking for Server socket channel
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Non-blocking Echo Server is running on port " + PORT);

            while (true) {
                selector.select(); // blocks until at least one channel is ready (i/o multiplexing)

                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();

                    if (key.isAcceptable()) {
                        handleAccept(serverChannel, selector);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    }

                    keyIterator.remove(); // remove the key after processing
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleAccept(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            clientChannel.configureBlocking(false); // setup non-blocking for Socket channel
            clientChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());
        }
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();

        int bytesRead = clientChannel.read(buffer); // non-blocking read
        if (bytesRead == 0) {
            System.out.println("No data available");
            return;
        }
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
