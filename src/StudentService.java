import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public List<Student> getAll() {
        return List.copyOf(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }
}