import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public void addAll(List<Student> newStudents) {
        students.addAll(newStudents);
    }

    public List<Student> getAll() {
        return List.copyOf(students);
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public List<String> getStudentsWithGradeAtLeast(double minGrade) {
        return students.stream()
                .filter(student -> student.getAverageGrade() >= minGrade)
                .sorted(Comparator.comparingDouble(Student::getAverageGrade).reversed())
                .map(student -> String.format("%s — %.2f", student.getName(), student.getAverageGrade()))
                .toList();
    }
}