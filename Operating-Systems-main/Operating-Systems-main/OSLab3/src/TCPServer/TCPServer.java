import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPServer {
    private static final int PORT = 12345;
    private static AtomicInteger messageCounter = new AtomicInteger(0);
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedWriter out;
        private BufferedReader in;
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        @Override
        public void run() {
            try {
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("login")) {
                        out.write("logged in\n");
                        out.flush();
                    } else if (inputLine.equals("logout")) {
                        out.write("logged out\n");
                        out.flush();
                        break;
                    } else {
                        out.write("echo: " + inputLine + "\n");
                        out.flush();
                    }
                    messageCounter.incrementAndGet();
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}