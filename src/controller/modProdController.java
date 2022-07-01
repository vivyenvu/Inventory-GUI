package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

public class modProdController implements Initializable {
    public Button modProdAddBtn;
    public Button modProdRemoveBtn;
    public Button modProdSaveBtn;
    public Button modProdCancelBtn;
    public TableView modProdMainTable;
    public TableView modProdAscPartTable;
    public TableColumn modProdMainTableID;
    public TableColumn modProdMainTableName;
    public TableColumn modProdMainTableStock;
    public TableColumn modProdMainTablePrice;
    public TableColumn modProdAscPartTableID;
    public TableColumn modProdAscPartTableName;
    public TableColumn modProdAscPartTableStock;
    public TableColumn modProdAscPartTablePrice;
    private ObservableList<Part> ascParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdMainTable.setItems(getAllParts());

        modProdMainTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modProdMainTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modProdMainTableStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modProdMainTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        modProdAscPartTable.setItems(ascParts);

        modProdAscPartTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modProdAscPartTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modProdAscPartTableStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modProdAscPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

    }

    public void onModProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onModProdAddBtn(ActionEvent actionEvent) {
    }

    public void onModProdRemoveBtn(ActionEvent actionEvent) {
    }

    public void onModProdSaveBtn(ActionEvent actionEvent) {
    }
}
