import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThemePark {
    private final int r;
    private final int k;
    private final int n;
    private final int[] sizes;
    private final long profit;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
                int t = in.nextInt();

                for (int i = 1; i <= t; ++i) {
                    int r = in.nextInt();
                    int k = in.nextInt();
                    int n = in.nextInt();
                    int[] sizes = new int[n];

                    for (int j = 0; j < n; ++j)
                        sizes[j] = in.nextInt();

                    ThemePark themePark = new ThemePark(r, k, n, sizes);

                    System.out.printf("Case #%d: %d\n", i,
                                      themePark.getProfit());
                }
            }
    }

    public ThemePark(int r, int k, int n, int[] sizes) {
        this.r = r;
        this.k = k;
        this.n = n;
        this.sizes = sizes;
        profit = calculateProfit();
    }

    private long calculateProfit() {
        // Mapping between a start position and the end position together with
        // the number of people that can board the roller coaster
        final Map<Integer, SimpleImmutableEntry<Integer, Long>>
            endPositionAndNumPeopleStartAt = new HashMap<>();
        long profit = 0;

        for (int i = 0, currentIndex = 0; i < r; ++i) {
            if (!endPositionAndNumPeopleStartAt.containsKey(currentIndex)) {
                SimpleImmutableEntry<Integer, Long> entry =
                    getEndPositionAndNumPeopleStartAt(currentIndex);
                endPositionAndNumPeopleStartAt.put(currentIndex, entry);
            }

            SimpleImmutableEntry<Integer, Long> entry =
                endPositionAndNumPeopleStartAt.get(currentIndex);
            currentIndex = entry.getKey();
            profit += entry.getValue();
        }

        return profit;
    }

    private SimpleImmutableEntry<Integer, Long>
        getEndPositionAndNumPeopleStartAt(int startIndex) {
        int currentIndex = startIndex;
        long sumPeople = 0;

        for (boolean firstTime = true; firstTime || currentIndex != startIndex
                 && currentIndex < n && sumPeople + sizes[currentIndex] <= k;
             currentIndex = (currentIndex + 1)
                 % n, firstTime = false)
            sumPeople += sizes[currentIndex];

        return new SimpleImmutableEntry<>(currentIndex, sumPeople);
    }

    public long getProfit() {
        return profit;
    }
}
