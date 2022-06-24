package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1080, 400));
        stage.show();
    }

    public static void main(String[] args) {


        // fruitTable.setItems(FruitBowl.getTheBowl());
        //mainPartTable.setItems(Inventory.getAllParts());
        launch(args);
    }
}
