import java.io.*;
import java.net.*;

/**
 * Chat Client
 * -----------
 * Connects to the chat server using sockets.
 * Supports:
 * 1. Sending messages to server
 * 2. Receiving messages from server (runs on separate thread)
 * 3. Commands like /quit, /pm, /list
 */
public class Client {

    // Default host and port
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

        String host = SERVER_HOST;
        int port = SERVER_PORT;

        // If user provides custom host/port in command line
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        try {

            // Connect to server
            Socket socket = new Socket(host, port);
            System.out.println("Connected to server.");

            // Streams for sending/receiving
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            // Listening thread for server messages
            Thread receiverThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = serverIn.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("You have been disconnected from the server.");
                }
            });

            receiverThread.start();

            // Main loop: send user input to server
            String input;
            while ((input = userInput.readLine()) != null) {
                serverOut.println(input);

                // Exit client
                if (input.equalsIgnoreCase("/quit")) {
                    break;
                }
            }

            receiverThread.join();
            socket.close();

        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
        }
    }
}
