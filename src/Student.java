public class Student {
    private final String name;
    private final double averageGrade;
    private final String recordBookNumber;

    public Student(String name, double averageGrade, String recordBookNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (averageGrade < 2 || averageGrade > 5) {
            throw new IllegalArgumentException(
                    "Средний балл должен быть от 2 до 5, получено: " + averageGrade);
        }
        if (recordBookNumber == null || !recordBookNumber.matches("\\d{6}")) {
            throw new IllegalArgumentException(
                    "Номер зачётки должен состоять из 6 цифр, получено: " + recordBookNumber);
        }

        this.name = name.trim();
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    public String getName() {
        return name;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return String.format("%s | Средний балл: %.2f | Зачетная книжка: %s", name, averageGrade, recordBookNumber);
    }
}