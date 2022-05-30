import services.Menu;
import services.config.DatabaseConfiguration;

public class Main {

    public static void main(String[] args) throws Exception {
////
        Menu.getInstance().startMenu();

        DatabaseConfiguration.closeDatabaseConnection();

    }
}
