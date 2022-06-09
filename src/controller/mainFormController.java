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

public class mainFormController implements Initializable {
    public Button mainAddPartBtn;
    public Button mainModPartBtn;
    public Button mainDeletePartBtn;
    public Button mainDeleteProdBtn;
    public Button mainModProdBtn;
    public Button mainAddProdBtn;
    public Button mainExitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("I am initialized!");
    }

    public void onClickMainAddPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainModPartBtn(ActionEvent actionEvent) {
        System.out.println("Modifying part");
    }

    public void onClickMainDeletePartBtn(ActionEvent actionEvent) {
        System.out.println("Deleting part");
    }

    public void onClickMainDeleteProdBtn(ActionEvent actionEvent) {
        System.out.println("Deleting product");
    }

    public void onClickMainModProdBtn(ActionEvent actionEvent) {
        System.out.println("Modifying product");
    }

    public void onClickMainAddProdBtn(ActionEvent actionEvent) {
        System.out.println("Adding product");
    }

    public void onClickMainExitBtn(ActionEvent actionEvent) {
        System.out.println("Exiting");
    }
}
