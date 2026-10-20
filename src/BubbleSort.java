import java.util.Comparator;
import java.util.List;

public class BubbleSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(List<T> items, Comparator<? super T> comparator) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (comparator.compare(items.get(j), items.get(j + 1)) > 0) {
                    T temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "Сортировка пузырьком";
    }
}