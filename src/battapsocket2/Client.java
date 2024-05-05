package battapsocket2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Nhập username từ client
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            out.println(username);

            // Luồng đọc dữ liệu từ server
            Thread readerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String message;
                    try {
                        while ((message = in.readLine()) != null) {
                            System.out.println(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readerThread.start();

            // Gửi tin nhắn từ client tới server
            String message;
            while (true) {
                message = scanner.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
