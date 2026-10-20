public final class InputParser {

    private InputParser() {
    }

    public static double parseGrade(String text) {
        try {
            return Double.parseDouble(text.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Средний балл должен быть числом, получено: " + text.trim());
        }
    }
}