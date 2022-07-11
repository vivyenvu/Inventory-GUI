package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/**
 * Application starts by showing the Main Form.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1080, 400));
        stage.show();
    }

    /**
     * This method holds example data to test application on.
     */
    private static void addTestData() {

        InHouse OPB = new InHouse(1, "OPB", 20.99, 15, 0, 50, 1234);
        InHouse DB = new InHouse (2, "Deadlift Bar", 30.99, 10, 0, 40, 2468);
        Outsourced plate = new Outsourced (3, "Eleiko plates", 10.99, 44, 0, 100, "Eleiko");
        Outsourced platform = new Outsourced (4, "Wood platform", 50.99, 12, 0, 20, "Woodies");

        Inventory.addPart(OPB);
        Inventory.addPart(DB);
        Inventory.addPart(plate);
        Inventory.addPart(platform);

        Product steel = new Product (11, "Steel Society", 65000.99, 1, 1, 3);
        Product crunch = new Product (12, "Crunch Johns Creek", 10000.99, 3, 1, 10);

        Inventory.addProduct(steel);
        Inventory.addProduct(crunch);
    }

    /**
     * This starts the program by adding the test data.
     * @param args argument to launch application with
     */
    public static void main(String[] args) {
        addTestData();
        launch(args);
    }
}
