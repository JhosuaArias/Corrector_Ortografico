public class Main {

    public static void main(String[] args) {
        OrthographicCorrector orthographicCorrector = new OrthographicCorrector();

        String query = "Curzos de pithon gratis";
        System.out.println(query);
        System.out.println(orthographicCorrector.correctOrthography(query));
    }
}
