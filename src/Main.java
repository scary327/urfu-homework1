void main() {
    Scanner scanner = new Scanner(System.in);
    StudentService service = new StudentService();
    new Menu(scanner, service).run();
}