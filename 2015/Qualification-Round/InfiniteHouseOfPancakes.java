import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class InfiniteHouseOfPancakes {
    static final int MAX_PIE = 1000;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 0; i < t; ++i) {
            int d = in.nextInt();

            int counts[] = new int[MAX_PIE + 1];
            final int maxPie;

            {
                int max = 0;

                for (int j = 0; j < d; ++j) {
                    int p = in.nextInt();
                    ++counts[p];

                    if (p > max) {
                        max = p;
                    }
                }

                maxPie = max;
            }

            int minMove = MAX_PIE;

            for (int pieInEachPlate = 1; pieInEachPlate <= maxPie; ++pieInEachPlate) {
                int countMove = 0;

                for (int j = 1; j < counts.length; ++j) {
                    countMove += (j - 1) / pieInEachPlate * counts[j];
                }

                int totalMove = countMove + pieInEachPlate;
                
                if (totalMove < minMove) {
                    minMove = totalMove;
                }
            }

            System.out.printf("Case #%d: %d\n", i + 1, minMove);
        }
    }
}
