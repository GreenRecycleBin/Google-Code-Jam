import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class FairWarning {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 1; i <= t; ++i) {
            int n = in.nextInt();
            BigInteger[] moments = new BigInteger[n];

            for (int j = 0; j < moments.length; ++j)
                moments[j] = new BigInteger(in.next());

            Arrays.sort(moments, Collections.reverseOrder());

            BigInteger[] diffs = new BigInteger[moments.length - 1];

            for (int j = 0; j < diffs.length ; ++j)
                diffs[j] = moments[j].subtract(moments[j + 1]);

            int indexFirstNonZero;

            for (indexFirstNonZero = 0; indexFirstNonZero < diffs.length;
                ++indexFirstNonZero) {
                if (diffs[indexFirstNonZero].equals(BigInteger.ZERO)) continue;

                break;
            }

            BigInteger gcd = diffs[indexFirstNonZero];

            for (int j = indexFirstNonZero + 1; j < diffs.length; ++j)
                if (!diffs[j].equals(BigInteger.ZERO))
                    gcd = gcd(diffs[j], gcd);

            System.out.printf("Case #%d: %s\n", i,
                              getSlarbosecondsFromReckoning(moments[0], gcd));
        }
    }

    private static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger remainder = a.remainder(b);

        if (remainder.equals(BigInteger.ZERO)) return b;

        return gcd(b, a.remainder(b));
    }

    private static BigInteger getSlarbosecondsFromReckoning(BigInteger a,
                                                            BigInteger b) {
        return a.negate().mod(b);
    }
}