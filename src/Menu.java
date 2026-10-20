import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final String DEFAULT_FILE = "students.csv";

    private final Scanner scanner;
    private final StudentService service;
    private final StudentFileReader fileReader = new StudentFileReader();
    private final List<SortAlgorithm<Student>> sortAlgorithms = List.of(
            new BubbleSort<>(),
            new InsertionSort<>()
    );

    public Menu(Scanner scanner, StudentService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void run() {
        while (true) {
            printMenu();
            System.out.print("Выберите пункт меню: ");
            String input = scanner.nextLine();

            int command;
            try {
                command = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: необходимо ввести число.");
                continue;
            }

            switch (command) {
                case 1 -> addManually();
                case 2 -> loadFromFile();
                case 3 -> processData();
                case 4 -> showAll();
                case 5 -> showByNumber();
                case 6 -> removeByNumber();
                case 7 -> sortStudents();
                case 0 -> {
                    System.out.println("Завершение работы программы...");
                    return;
                }
                default -> System.out.println("Неизвестная команда.");
            }
        }
    }

    private void printMenu() {
        System.out.println("""

                ===== МЕНЮ =====
                1. Добавить студента вручную
                2. Загрузить студентов из файла
                3. Обработать данные
                4. Показать всех студентов
                5. Показать студента по номеру
                6. Удалить студента по номеру
                7. Отсортировать студентов
                0. Выход""");
    }

    private void addManually() {
        try {
            System.out.print("Введите ФИО: ");
            String name = scanner.nextLine();
            System.out.print("Введите средний балл (от 2 до 5): ");
            double averageGrade = InputParser.parseGrade(scanner.nextLine());
            System.out.print("Введите номер зачётки (6 цифр): ");
            String recordBookNumber = scanner.nextLine().trim();

            service.add(new Student(name, averageGrade, recordBookNumber));
            System.out.println("Студент добавлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        System.out.print("Введите путь к файлу (Enter — " + DEFAULT_FILE + "): ");
        String input = scanner.nextLine().trim();

        try {
            Path path = Path.of(input.isEmpty() ? DEFAULT_FILE : input);
            List<Student> loaded = fileReader.read(path);
            service.addAll(loaded);
            System.out.println("Загружено студентов: " + loaded.size());
        } catch (InvalidPathException e) {
            System.out.println("Ошибка: некорректный путь: " + input);
        } catch (NoSuchFileException e) {
            System.out.println("Ошибка: файл не найден: " + e.getFile());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void processData() {
        if (service.isEmpty()) {
            System.out.println("Список пуст — сначала добавьте студентов.");
            return;
        }

        System.out.print("Минимальный средний балл: ");
        double minGrade;
        try {
            minGrade = InputParser.parseGrade(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }

        List<String> result = service.getStudentsWithGradeAtLeast(minGrade);
        if (result.isEmpty()) {
            System.out.println("Нет студентов с баллом от " + minGrade);
            return;
        }
        System.out.println("Студенты с баллом от " + minGrade + " (по убыванию балла):");
        result.forEach(System.out::println);
    }

    private void showAll() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        printNumbered(service.getAll());
    }

    private void showByNumber() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        int number = readOption("Номер студента (1-" + service.size() + "): ", service.size());
        System.out.println(service.get(number - 1));
    }

    private void removeByNumber() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        int number = readOption("Номер студента для удаления (1-" + service.size() + "): ", service.size());
        Student removed = service.remove(number - 1);
        System.out.println("Удалён: " + removed);
    }

    private void sortStudents() {
        if (service.isEmpty()) {
            System.out.println("Список пуст — сначала добавьте студентов.");
            return;
        }

        SortAlgorithm<Student> algorithm = chooseAlgorithm();
        Comparator<Student> comparator = chooseComparator();

        List<Student> sorted = service.getSorted(algorithm, comparator);
        System.out.println("Результат (" + algorithm.getName() + "):");
        printNumbered(sorted);
    }

    private SortAlgorithm<Student> chooseAlgorithm() {
        System.out.println("Алгоритм сортировки:");
        for (int i = 0; i < sortAlgorithms.size(); i++) {
            System.out.println((i + 1) + ". " + sortAlgorithms.get(i).getName());
        }
        int choice = readOption("Выберите алгоритм: ", sortAlgorithms.size());
        return sortAlgorithms.get(choice - 1);
    }

    private Comparator<Student> chooseComparator() {
        System.out.println("""
                Поле для сортировки:
                1. Средний балл
                2. ФИО""");
        int field = readOption("Выберите поле: ", 2);
        Comparator<Student> comparator = field == 1
                ? Comparator.comparingDouble(Student::getAverageGrade)
                : Comparator.comparing(Student::getName);

        System.out.println("""
                Направление:
                1. По возрастанию
                2. По убыванию""");
        int direction = readOption("Выберите направление: ", 2);
        return direction == 1 ? comparator : comparator.reversed();
    }

    private int readOption(String prompt, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int option = Integer.parseInt(scanner.nextLine().trim());
                if (option >= 1 && option <= max) {
                    return option;
                }
            } catch (NumberFormatException e) {
                // некорректный ввод — сообщение ниже
            }
            System.out.println("Ошибка: введите число от 1 до " + max + ".");
        }
    }

    private void printNumbered(List<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
    }
}