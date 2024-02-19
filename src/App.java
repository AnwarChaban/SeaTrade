import object.*;

public class App {
    public static void main(String[] args) throws Exception {
        new Company("company").instantiate();
        System.out.println("New Company instance!");

        new Ship("ship", "company").instantiate();
        System.out.println("New Ship instance!");
    }
}
