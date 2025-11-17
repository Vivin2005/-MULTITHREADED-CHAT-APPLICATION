import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Multi-threaded Chat Server
 * ---------------------------
 * This server handles multiple clients using Java Sockets.
 * Each client runs on its own thread. Messages are broadcast
 * to all connected users unless a private message command is used.
 *
 * Features:
 * 1. Multiple client support using threads.
 * 2. Real-time broadcast messaging.
 * 3. Private messaging (PM).
 * 4. Online user list.
 * 5. Clean disconnect handling.
 */
public class Server {

    // Port number on which the server runs
    private static final int PORT = 12345;

    // Thread-safe map of connected clients: username → ClientHandler
    private static final ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("Server starting on port: " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            // Server accepts client connections forever
            while (true) {
                Socket clientSocket = serverSocket.accept();   // Wait for the next client
                System.out.println("Client connected: " + clientSocket);

                // Handle each client in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Server Error: " + e.getMessage());
        }
    }

    /** 
     * Broadcasts a message to all connected clients.
     * excludeUser → username to skip (usually the sender)
     */
    public static void broadcast(String message, String excludeUser) {
        for (ClientHandler handler : clients.values()) {
            if (excludeUser != null && excludeUser.equals(handler.getUsername())) continue;
            handler.sendMessage(message);
        }
    }

    /**
     * Sends a private message to a specific user.
     */
    public static boolean sendPrivate(String toUser, String message) {
        ClientHandler handler = clients.get(toUser);
        if (handler != null) {
            handler.sendMessage(message);
            return true;
        }
        return false;
    }

    /**
     * Returns a comma-separated list of online users.
     */
    public static String getUserList() {
        return String.join(", ", clients.keySet());
    }

    /**
     * Registers a new client if the username is unique.
     */
    public static boolean registerUser(String username, ClientHandler handler) {
        return clients.putIfAbsent(username, handler) == null;  // null -> success
    }

    /**
     * Removes a client from the map after disconnect.
     */
    public static void unregisterUser(String username) {
        clients.remove(username);
    }

    /**
     * Handles communication with a single client.
     * Each connected client has its own ClientHandler thread.
     */
    static class ClientHandler implements Runnable {

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public String getUsername() {
            return username;
        }

        /**
         * Sends a message to this client only.
         */
        public void sendMessage(String msg) {
            out.println(msg);
        }

        /**
         * The run() method executes when the thread starts.
         * It handles:
         * - username registration
         * - receiving messages
         * - processing commands
         * - disconnect logic
         */
        @Override
        public void run() {
            try {
                // Creating input/output streams for client communication
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask for a username
                out.println("Enter username:");
                String name = in.readLine();
                if (name == null) return;

                name = name.trim();

                // Ensure unique username
                while (name.isEmpty() || !registerUser(name, this)) {
                    out.println("Username already taken or invalid. Enter another:");
                    name = in.readLine();
                    if (name == null) return;
                    name = name.trim();
                }

                this.username = name;
                System.out.println(username + " has joined.");

                // Notify everyone
                broadcast("[" + username + "] joined the chat.", username);

                // Help message
                out.println("Welcome " + username + "! Commands: /quit | /list | /pm <user> <msg>");

                String message;

                // Continuously read messages from this client
                while ((message = in.readLine()) != null) {

                    message = message.trim();

                    // Ignore empty messages
                    if (message.isEmpty()) continue;

                    // === COMMANDS ===
                    if (message.equalsIgnoreCase("/quit")) {
                        out.println("You have disconnected.");
                        break; // exits loop → disconnect
                    }

                    else if (message.equalsIgnoreCase("/list")) {
                        out.println("Online Users: " + getUserList());
                    }

                    else if (message.startsWith("/pm ")) {
                        // Private message format: /pm username message text
                        String[] parts = message.split("\\s+", 3);
                        if (parts.length < 3) {
                            out.println("Usage: /pm <user> <message>");
                        } else {
                            String targetUser = parts[1];
                            String privateMsg = parts[2];

                            boolean sent = sendPrivate(targetUser,
                                    "[PM from " + username + "]: " + privateMsg);

                            if (sent) {
                                out.println("[PM to " + targetUser + "]: " + privateMsg);
                            } else {
                                out.println("User not found: " + targetUser);
                            }
                        }
                    }

                    // === NORMAL BROADCAST MESSAGE ===
                    else {
                        broadcast("[" + username + "]: " + message, null);
                    }
                }

            } catch (IOException e) {
                System.err.println("Error with client " + username + ": " + e.getMessage());
            }

            // === CLEANUP ===
            finally {
                try {
                    if (username != null) {
                        unregisterUser(username);
                        broadcast("[" + username + "] left the chat.", username);
                        System.out.println(username + " disconnected.");
                    }
                    if (socket != null) socket.close();

                } catch (IOException ignored) {}
            }
        }
    }
}
