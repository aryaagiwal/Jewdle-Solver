import java.util.Set;

public class Statistics {
    public void getData(Set<String> temp) {
        for (int i = 'a'; i <= 'z'; i++) {
            int[] currentCounts = new int[6];
            for (String word : temp) {
                for (int c = 0; c < 6; c++) {
                    if (word.charAt(c) == i) {
                        currentCounts[c]++;
                    }
                }
            }
            System.out.println("{" + currentCounts[0] + ", " + currentCounts[1] + ", " + currentCounts[2] + ", " + currentCounts[3] + ", " + currentCounts[4] + ", " + currentCounts[5] + "}," );
        }
    }
}
