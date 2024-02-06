import object.*;

public class App {
    public static void main(String[] args) throws Exception {
        new Ship("ship", "company").instantiate();
        System.out.println("New Ship instance!");
    }
}
