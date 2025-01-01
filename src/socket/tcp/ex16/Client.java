package socket.tcp.ex16;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private BufferedReader userIn;

    public void start(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        userIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connected to server: " + socket.getInetAddress());

        while (true) {
            String command = userIn.readLine();
            StringTokenizer st = new StringTokenizer(command);
            String cmd = st.nextToken();
            StringBuilder sb = new StringBuilder();
            while (st.hasMoreTokens()) {
                sb.append(st.nextToken());
                if (st.hasMoreTokens()) {
                    sb.append(" ");
                }
            }
            String target = sb.toString().trim();
            if (cmd.equalsIgnoreCase("EXIT")) {
                writer.println("EXIT");
                break;
            } else if (cmd.equals("UPLOAD")) {
               
                uploadFile(target);
            } else {
                System.out.println("Unknown command. Supported commands: UPLOAD, EXIT");
            }
        }

        socket.close();
        userIn.close();
        reader.close();
        writer.close();
    }

    private void uploadFile(String target) {
        StringTokenizer tokens = new StringTokenizer(target);
        

        String source = tokens.nextToken();
        String dest = tokens.nextToken();
        try {
            File file = new File(source);
            if (!file.exists()) {
                System.out.println("Source file does not exist.");
                return;
            }
            writer.println("UPLOAD " + dest);

            FileInputStream fis = new FileInputStream(file);
            OutputStream os = socket.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            os.flush();
            fis.close();

            System.out.println("File uploaded successfully: " + dest);

            // Nhận phản hồi từ Server
            String response = reader.readLine();
            if (response != null) {
                System.out.println("Server: " + response);
            }

        } catch (IOException e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.start("localhost", 2000);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
