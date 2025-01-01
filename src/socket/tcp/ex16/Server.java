package socket.tcp.ex16;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {
    private ServerSocket serverSocket;

    // bật server
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket); // xử lý request từ client
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // đọc thông tin từ client gửi về
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            while (true) {
                String request = reader.readLine();
                StringTokenizer st = new StringTokenizer(request);
                String cmd = st.nextToken();
                String target = st.nextToken();
                if (cmd.equals("exit")) {
                    break;
                }
                if (cmd.equals("UPLOAD")) {
                    receiveFile(clientSocket.getInputStream(), target);
                } else {
                    writer.println("Invalid request");
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private void receiveFile(InputStream is, String destPath) {
        try (FileOutputStream fos = new FileOutputStream(destPath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(2000);
    }
}
