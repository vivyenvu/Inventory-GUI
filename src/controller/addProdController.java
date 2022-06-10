package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addProdController implements Initializable {
    public Button addProdAddBtn;
    public Button addProdRemoveBtn;
    public Button addProdSaveBtn;
    public Button addProdCancelBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAddProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddProdAddBtn(ActionEvent actionEvent) {
    }

    public void onAddProdRemoveBtn(ActionEvent actionEvent) {
    }

    public void onAddProdSaveBtn(ActionEvent actionEvent) {
    }
}
