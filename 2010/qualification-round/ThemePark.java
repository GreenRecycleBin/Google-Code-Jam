import static java.util.AbstractMap.SimpleImmutableEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ThemePark {
    private final int r;
    private final int k;
    private final int n;
    private final int[] sizes;

    // Mapping between a start position and the end position together with
    // the number of people that can board the roller coaster
    private final Map<Integer, SimpleImmutableEntry<Integer, Long>>
        endPositionAndNumPeopleStartAt;
    private final long profit;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 1; i <= t; ++i) {
            int r = in.nextInt();
            int k = in.nextInt();
            int n = in.nextInt();
            int[] sizes = new int[n];

            for (int j = 0; j < n; ++j)
                sizes[j] = in.nextInt();

            ThemePark themePark = new ThemePark(r, k, n, sizes);

            System.out.printf("Case #%d: %d\n", i, themePark.getProfit());
        }
    }

    public ThemePark(int r, int k, int n, int[] sizes) {
        this.r = r;
        this.k = k;
        this.n = n;
        this.sizes = sizes;
        endPositionAndNumPeopleStartAt = new HashMap<>();
        profit = calculateProfit();
    }

    private long calculateProfit() {
        long profit = 0;

        for (int i = 0, currentIndex = 0; i < r; ++i) {
            if (endPositionAndNumPeopleStartAt.containsKey(currentIndex)) {
                SimpleImmutableEntry<Integer, Long> entry
                    = endPositionAndNumPeopleStartAt.get(currentIndex);
                currentIndex = entry.getKey();
                profit += entry.getValue();
            } else {
                int startIndex = currentIndex;
                long sumPeople = 0;

                for (boolean firstTime = true; firstTime
                         || currentIndex != startIndex
                         && currentIndex < n
                         && sumPeople + sizes[currentIndex] <= k;
                     currentIndex = (currentIndex + 1) % n, firstTime = false)
                    sumPeople += sizes[currentIndex];

                profit += sumPeople;
                endPositionAndNumPeopleStartAt
                    .put(startIndex, new SimpleImmutableEntry<>(currentIndex,
                                                                sumPeople));
            }
        }

        return profit;
    }

    public long getProfit() {
        return profit;
    }
}
