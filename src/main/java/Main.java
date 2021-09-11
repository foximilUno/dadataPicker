public class Main {

    public static void main(String[] args) {
        String token = "9eb1936c1f0ad26a3e06487d115e18493e65816c";
        DadataProvider provider = new DadataProvider(token);
        provider.PrintSortedByRegistrationDateAsc("Мос",20);
    }
}
