package socket.tcp.ex18;

public record User(String username, String password) {
    public User {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
} 