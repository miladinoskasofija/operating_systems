package TCPClient;

import java.io.*;
import java.net.*;

public class TCPClient {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Connected to server. Type 'login' to log in.");
            out.write("login\n");
            out.flush();
            System.out.println("Server response: " + in.readLine());
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.write(userInput + "\n");
                out.flush();
                String response = in.readLine();
                System.out.println("Server response: " + response);
                if (response.equals("logged out")) {
                    System.out.println("Logged out. Closing connection.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}