package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        stage.setTitle("Main Form");
        stage.setScene(new Scene(root, 1080, 400));
        stage.show();
    }

    public static void main(String[] args) {
        InHouse OPB = new InHouse(1, "OPB", 200, 15, 0, int 50, 1234, true);
        InHouse DB = new InHouse (2, "Deadlift Bar", 300, 10, 0, 40, 2468, true);

        launch(args);
    }
}
