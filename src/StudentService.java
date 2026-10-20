import java.util.Comparator;
import java.util.List;

public class StudentService {
    private final MyArrayList<Student> students = new MyArrayList<>();

    public void add(Student student) {
        students.add(student);
    }

    public void addAll(List<Student> newStudents) {
        for (Student student : newStudents) {
            students.add(student);
        }
    }

    public Student get(int index) {
        return students.get(index);
    }

    public Student remove(int index) {
        return students.remove(index);
    }

    public int size() {
        return students.size();
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public List<Student> getAll() {
        return students.stream().toList();
    }

    public List<String> getStudentsWithGradeAtLeast(double minGrade) {
        return students.stream()
                .filter(student -> student.getAverageGrade() >= minGrade)
                .sorted(Comparator.comparingDouble(Student::getAverageGrade).reversed())
                .map(student -> String.format("%s — %.2f", student.getName(), student.getAverageGrade()))
                .toList();
    }
}