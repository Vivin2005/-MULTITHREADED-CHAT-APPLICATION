# -MULTITHREADED-CHAT-APPLICATION

COMPANY NAME : CODETECH IT SOLUTION

NAME : Vivin S.R

INTERN ID : CT06DR125

DOMAIN : Java Programming

DURATION : 6 WEEKS

MENTOR : NEELA SANTOSH

** DESCRIPTION ABOUT THE TASK **

🌐 TASK 1: MULTITHREADED CHAT APPLICATION 💬⚙️
🎯 Objective:

➡️ The main objective of this task is to build a Java-based multithreaded client–server chat application where multiple users can communicate with each other in real time. This project demonstrates the core principles of socket programming, concurrency, and network-based communication, which are crucial for developing scalable and interactive distributed systems.

➡️ The system showcases how a central server manages multiple client connections simultaneously and how messages are transmitted across devices using basic TCP networking.

⚙️ Overview:

➡️ In modern communication platforms, real-time data exchange is an essential feature. Applications like WhatsApp, Discord, Slack, and Telegram rely on fast and reliable message transfer powered by client–server architectures.

➡️ This task implements a console-based chat system using Java Sockets, where:

The server listens for incoming client connections.

Each client can send and receive messages instantly.

The server uses multithreading so that each connected user is handled independently without delays or blocking.

➡️ The application supports:

Public chat communication

Private messaging

User listing

Graceful disconnection

Automatic broadcast of join/leave notifications

➡️ By developing this application, real-world networking concepts such as parallel processing, connection handling, stream communication, and network data routing are explored in depth.

💻 Technology Used:
Programming Language:

➡️ Java (JDK 17)

Core Libraries:

➡️ java.net.ServerSocket — for creating the server
➡️ java.net.Socket — for client-server communication
➡️ java.io — for input/output stream handling
➡️ java.util.concurrent.ConcurrentHashMap — for thread-safe user management
➡️ Multithreading (Threads + Runnable)

Tools Used:

➡️ Visual Studio Code
➡️ Terminal
➡️ Windows 10 / 11 environment

🧠 Working Principle:

➡️ The program starts with a server that listens on a specific port (12345). When a new client connects, the server creates a new thread to handle that client. This ensures multiple clients can chat without waiting for each other — a core concept of concurrency.

➡️ Each client:

Connects to the server using TCP sockets

Sends messages to the server

Listens continuously for incoming messages from other users

➡️ The server maintains a thread-safe map to keep track of connected users and their usernames.

➡️ The message flow works like this:

1️⃣ Client connects → Server assigns a thread
2️⃣ User enters a username → Server registers it
3️⃣ Messages typed by the user → Sent to server
4️⃣ Server broadcasts message → Sent to all connected clients
5️⃣ Private messages are routed only to the intended user
6️⃣ When a user disconnects → Server notifies others and cleans up resources

➡️ The system includes custom commands such as:
/list, /pm <user> <msg>, /quit, and automatic join/leave notifications.

🧰 Features:

✔ Supports multiple clients in real-time
✔ Uses TCP Socket communication
✔ Multithreaded independent client handling
✔ Broadcast messaging across all users
✔ Private messaging using commands
✔ Active user list
✔ Cleanly handles disconnect events
✔ Thread-safe username management
✔ Console-based, fast, responsive interaction

🧪 Sample Output (Server & Client Interaction):

Server Output:

Server starting on port: 12345
Vivin joined the chat.
Arun joined the chat.
Arun disconnected.


Client Output:

Enter username:
Vivin

[Arun joined the chat]
Hello everyone!
[PM from Arun]: Bro are you there?


This confirms that multithreading, broadcasting, and private messaging all work seamlessly.

🚀 Conclusion:

➡️ This project successfully demonstrates how Java can be used to build scalable, real-time communication platforms using core networking principles.

➡️ Through this task, strong understanding was gained in:

Socket-level communication

Multithreading and concurrency

Client–server architecture

Real-time data broadcasting

Thread synchronization

Connection lifecycle management

➡️ The knowledge and skills developed here directly apply to building:

Chat applications

Multiplayer game servers

Real-time dashboards

Collaboration tools

IoT communication systems

➡️ Overall, this project fulfills the internship requirement by showcasing practical backend development skills, strong problem-solving, and the ability to design interactive systems that communicate over networks. 💬⚡🌐

** OUTPUT **

