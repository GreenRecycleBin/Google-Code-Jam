import java.util.Scanner;

public class StandingOvation {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        for (int i = 0; i < t; ++i) {
            int size = in.nextInt();
            String s = in.next();

            int stoodUpPeopleCount = 0;
            int minInvitedFriends = 0;

            for (int j = 0; j <= size; ++j) {
                minInvitedFriends = Math.max(minInvitedFriends, j - stoodUpPeopleCount);
                stoodUpPeopleCount += s.charAt(j) - '0';
            }

            System.out.printf("Case #%d: %d\n", i + 1, minInvitedFriends);
        }
    }
}
