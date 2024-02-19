import object.*;

public class App {
    public static void main(String[] args) throws Exception {
        new Company("test3").instantiate();
        System.out.println("New Company instance!");

        // new Ship("test2", "test").instantiate();
        // System.out.println("New Ship instance!");
    }
}
