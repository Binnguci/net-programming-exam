package socket.tcp.ex17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader reader, userIn;
    private PrintWriter writer;

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
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            userIn = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String request = reader.readLine();
                if (request.equalsIgnoreCase("EXIT")) {
                    break;
                } else {
                    String response = caculation(request);
                    writer.println(response);
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private String caculation(String request) {
        StringTokenizer st = new StringTokenizer(request, "+-*/", true);
        if (st.countTokens() != 3) {
            return "Invalid request";
        }
        String operand1 = st.nextToken();
        String operator = st.nextToken();
        String operand2 = st.nextToken();
        double num1, num2;
        try {
            num1 = Double.parseDouble(operand1);
        } catch (NumberFormatException e) {
            return "Invalid operand: " + operand1;
        }
        try {
            num2 = Double.parseDouble(operand2);
        } catch (NumberFormatException e) {
            return "Invalid operand: " + operand2;
        }
        double result = 0.0;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (Double.compare(num2, 0.0) == 0) {
                    return "Cannot divide by zero";
                }
                result = num1 / num2;
            default:
                return "Invalid operator: " + operator;
        }
        return operand1 + " " + operator + " " + operand2 + " = " + result;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(12345);
    }
}
