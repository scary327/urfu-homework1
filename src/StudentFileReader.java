import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class StudentFileReader {
    private static final String SEPARATOR = ";";

    public List<Student> read(Path path) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    students.add(parseLine(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Строка " + lineNumber + " пропущена: " + e.getMessage());
                }
            }
        }

        return students;
    }

    private Student parseLine(String line) {
        String[] parts = line.split(SEPARATOR);
        if (parts.length != 3) {
            throw new IllegalArgumentException("ожидалось 3 поля через '" + SEPARATOR + "'");
        }
        String name = parts[0].trim();
        double averageGrade = InputParser.parseGrade(parts[1]);
        String recordBookNumber = parts[2].trim();
        return new Student(name, averageGrade, recordBookNumber);
    }
}