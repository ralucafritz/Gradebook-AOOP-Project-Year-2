import services.Menu;
import services.config.DatabaseConfiguration;

public class Main {

    public static void main(String[] args)   {

        Menu.getInstance().startMenu();

        DatabaseConfiguration.closeDatabaseConnection();

    }
}
