package battapsocket2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 23456;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running. Waiting for clients...");

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Yêu cầu client nhập username trước khi kết nối
                out.println("Enter your username:");
                username = in.readLine();
                broadcast(username + " has joined the chat");

                clientWriters.add(out);

                String message;
                while ((message = in.readLine()) != null) {
                    broadcast(username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(username + " has left the chat");
            } finally {
                if (username != null) {
                    clientWriters.remove(out);
                    broadcast(username + " has left the chat");
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(String message) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }
}

