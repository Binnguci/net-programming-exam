package socket.rmi.ex19;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    private Registry registry;
    void start(int port) {
        try {
            registry = LocateRegistry.createRegistry(port);
            IStudent studentService = new StudentImpl();
            System.out.println("Server is running on port " + port);
            registry.rebind("STUDENT", studentService);
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.start(2000);
    }
}
