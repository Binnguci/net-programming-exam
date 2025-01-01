package socket.tcp.ex18;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isLogin = false;
    private String currentUsername;
    private List<Student> students = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public Server() {
        students.add(new Student("21130291", "Nguyen Van A", 2000, 3.5));
        students.add(new Student("21130765", "Tran Thi B", 2001, 3.0));
        students.add(new Student("21134627", "Le Van C", 2002, 3.2));

        users.add(new User("admin", "admin"));
        users.add(new User("user", "user"));
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        boolean running = true; // Cờ hiệu để thoát khỏi vòng lặp khi QUIT
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            while (running) {
                String request = reader.readLine();
                if (request == null) {
                    break; // Ngắt kết nối nếu client đóng luồng
                }

                StringTokenizer st = new StringTokenizer(request);
                String cmd = st.nextToken();
                String response = "";

                switch (cmd) {
                    case "QUIT":
                        response = "Goodbye!";
                        running = false; // Thoát vòng lặp
                        break;
                    case "USERNAME":
                        if (!isLogin) {
                            response = checkUsername(st.nextToken());
                        } else {
                            response = "Ban da dang nhap roi.";
                        }
                        break;
                    case "PASSWORD":
                        if (!isLogin) {
                            response = checkPassword(st.nextToken());
                        } else {
                            response = "Ban da dang nhap roi.";
                        }
                        break;
                    case "FINDBYID":
                        if (!isLogin) {
                            response = "Vui long dang nhap.";
                        } else {
                            response = findByID(st.nextToken());
                        }
                        break;
                    case "FINDBYNAME":
                        if (!isLogin) {
                            response = "Vui long dang nhap.";
                        } else {
                            response = findByName(st.nextToken(""));
                        }
                        break;
                    default:
                        response = "Lenh khong hop le.";
                        break;
                }
                writer.println(response);
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    private String checkUsername(String username) {
        for (User user : users) {
            if (user.username().equals(username)) {
                currentUsername = username;
                return "Vui long nhap password";
            }
        }
        return "Ten dang nhap sai";
    }

    private String checkPassword(String password) {
        for (User user : users) {
            if (user.password().equals(password) && user.username().equals(currentUsername)) {
                isLogin = true;
                return "Dang nhap thanh cong";
            }
        }
        return "Mat khau sai";
    }

    private String findByID(String id) {
        for (Student student : students) {
            if (student.id().equals(id)) {
                return student.toString();
            }
        }
        return "Khong tim thay sinh vien";
    }

    private String findByName(String name) {
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            if (student.name().toLowerCase().contains(name.toLowerCase())) {
                result.append(student.toString()).append("\n");
            }
        }
        return result.length() > 0 ? result.toString() : "Khong tim thay sinh vien";
    }
    

    public static void main(String[] args) {
        Server server = new Server();
        server.start(54321);
    }
}
