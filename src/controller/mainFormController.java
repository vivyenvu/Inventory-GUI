package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

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
    public TableView mainPartTable;
    public TableColumn mainPartTableID;
    public TableColumn mainPartTableName;
    public TableColumn mainPartTableInventory;
    public TableColumn mainPartTablePrice;
    public TableView mainProdTable;
    public TableColumn mainProdTableID;
    public TableColumn mainProdTableName;
    public TableColumn mainProdTableInventory;
    public TableColumn mainProdTablePrice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartTable.setItems(Inventory.getAllParts());

        mainPartTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        mainPartTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        mainPartTableInventory.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        mainPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }

    public void onClickMainAddPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainModPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainDeletePartBtn(ActionEvent actionEvent) {
        System.out.println("Deleting part");
    }

    public void onClickMainDeleteProdBtn(ActionEvent actionEvent) {
        System.out.println("Deleting product");
    }

    public void onClickMainModProdBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modProd.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainAddProdBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProd.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainExitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
