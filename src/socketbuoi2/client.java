package socketbuoi2;

import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 34567);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String number;
            while ((number = in.readLine()) != null) {
                System.out.println("Received number: " + number);
            }

            // Đóng kết nối với server
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

