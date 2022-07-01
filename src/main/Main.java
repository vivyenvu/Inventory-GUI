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

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1080, 400));
        stage.show();
    }
    private static void addTestData() {

        InHouse OPB = new InHouse(1, "OPB", 20.543, 15, 0, 50, 1234, true);
        InHouse DB = new InHouse (2, "Deadlift Bar", 30.10, 10, 0, 40, 2468, true);
        Outsourced plate = new Outsourced (3, "Eleiko plates", 10.00, 44, 0, 100, "Eleiko", false);
        Outsourced platform = new Outsourced (4, "Wood platform", 50, 12, 0, 20, "Woodies", false);

        Inventory.addPart(OPB);
        Inventory.addPart(DB);
        Inventory.addPart(plate);
        Inventory.addPart(platform);

        Product steel = new Product (11, "Steel Society", 65000, 1, 1, 3);
        Product crunch = new Product (12, "Crunch Johns Creek", 10000, 3, 1, 10);

        Inventory.addProduct(steel);
        Inventory.addProduct(crunch);
    }
    public static void main(String[] args) {
        addTestData();
        launch(args);
    }
}
