package socket.rmi.ex19;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Registry registry;
    private BufferedReader userIn;

    void run() {
        try {
            registry = LocateRegistry.getRegistry("localhost", 2000);
            IStudent studentService = (IStudent) registry.lookup("STUDENT");
            userIn = new BufferedReader(new InputStreamReader(System.in));
            String input;

            while (true) {
                input = userIn.readLine();
                if (input == null || input.equalsIgnoreCase("QUIT")) {
                    System.out.println("Exiting...");
                    break;
                }

                String[] parts = input.split(" ", 2);
                String command = parts[0];

                switch (command.toUpperCase()) {
                    case "FIND_BY_ID":
                        if (parts.length < 2) {
                            System.out.println("Usage: FIND_BY_ID <id>");
                            break;
                        }
                        System.out.println("Student: " + studentService.findById(parts[1]));
                        break;

                    case "FIND_BY_NAME":
                        if (parts.length < 2) {
                            System.out.println("Usage: FIND_BY_NAME <name>");
                            break;
                        }
                        System.out.println("Student: " + studentService.findByName(parts[1]));
                        break;
                    default:
                        System.out.println("Unknown command. Please try again!");
                        break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error running client: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().run();
    }
}
