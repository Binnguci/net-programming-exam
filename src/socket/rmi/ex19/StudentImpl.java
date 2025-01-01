package socket.rmi.ex19;

import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class StudentImpl extends UnicastRemoteObject implements IStudent {
    private static final long serialVersionUID = 1L;
    List<Student> students = new ArrayList<>();


    public StudentImpl() throws Exception {
        super();
        students.add(new Student("21130291", "Nguyen Van Hong", 2000, 3.5));
        students.add(new Student("21130765", "Tran Thi Lai", 2001, 3.0));
        students.add(new Student("21134627", "Le Van Binh", 2002, 3.2));
    }

    @Override
    public String findByName(String name) {
        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            if (student.name().toLowerCase().contains(name.toLowerCase())) {
                result.append(student.toString()).append("\n");
            }
        }
        return result.length() > 0 ? result.toString() : "Khong tim thay sinh vien";
    }

    @Override
    public String findById(String id) {
       if (students.stream().anyMatch(st -> st.id().equals(id))){
           return students.stream().filter(st -> st.id().equals(id)).findFirst().get().toString();
       }
       return "Not found";
    }
    
}
