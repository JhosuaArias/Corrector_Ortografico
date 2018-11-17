public class Main {

    public static void main(String[] args) {

        String query = "Curzos de pithon gratis";
        System.out.println(query);
        double startTime = System.nanoTime();
        System.out.println(OrthographicCorrector.correctOrthography(query));
        double endTime = System.nanoTime();

        System.out.println((endTime - startTime)/1000000);

        query = "Me cago en la reputisima madre que la remilpario";
        System.out.println(query);
        System.out.println(OrthographicCorrector.correctOrthography(query));
    }
}
