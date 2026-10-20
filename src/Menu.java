import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final StudentService service;

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
                0. Выход""");
    }

    private void addManually() {
        System.out.println("TODO: добавление вручную");
    }

    private void loadFromFile() {
        System.out.println("TODO: загрузка из файла");
    }

    private void processData() {
        System.out.println("TODO: обработка данных");
    }

    private void showAll() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }
        service.getAll().forEach(System.out::println);
    }
}