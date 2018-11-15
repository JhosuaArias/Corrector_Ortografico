public class DistanceCalculator {

    static int computeLevenshteinDistance(String string1, String string2){
        final int X = string1.length();
        final int Y = string2.length();
        int[][] distance = new int[X+1][Y+1];

        int cost;

        for (int i = 0; i <= X; i++) {
            distance[i][0] = i;
        }
        for (int j = 0; j <= Y; j++) {
            distance[0][j] = j;
        }


        for (int i = 1; i <= X; i++) {
            for (int j = 1; j <= Y; j++) {

                cost = (string1.charAt(i-1) == string2.charAt(j-1))? 0 : 1;

                distance[i][j] = min(
                        distance[i-1][j] + 1,       // Deletion
                        distance[i][j-1] + 1,       // Insertion
                        distance[i-1][j-1] + cost // Substitution
                );

            }
        }

        return distance[X][Y];
    }

    /**
     * Minimum between 3 numbers
     * @param n1 first number to compare
     * @param n2 second number to compare
     * @param n3 third number to compare
     * @return the smallest number
     */
    private static int min(int n1, int n2, int n3) {
        if (n1 < n2 && n1 < n3)
            return n1;

        if (n2 < n1 && n2 < n3)
            return n2;

        return n3;
    }

}
