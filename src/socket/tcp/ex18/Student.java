package socket.tcp.ex18;

public record Student(String id, String name, int yearOfBirth, double gpa) {
    public Student {
        if (yearOfBirth < 1900 || yearOfBirth > 2021) {
            throw new IllegalArgumentException("Invalid year of birth");
        }
        if (gpa < 0 || gpa > 4) {
            throw new IllegalArgumentException("Invalid GPA");
        }
    }
}