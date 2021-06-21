
import Controller.ControllerInici;
import Data.DataImport;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        ControllerInici ci = new ControllerInici(DataImport.loadCompeticio(), DataImport.loadTemes());

        ci.menu();

    }
}
