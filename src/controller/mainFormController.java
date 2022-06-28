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
import model.*;

import javafx.scene.control.TextField;
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
    public TextField queryPartSearch;
    public TextField queryProdSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartTable.setItems(Inventory.getAllParts());

        mainPartTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        mainPartTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        mainPartTableInventory.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        mainPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        mainProdTable.setItems(Inventory.getAllProducts());

        mainProdTableID.setCellValueFactory(new PropertyValueFactory<>("prodID"));
        mainProdTableName.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        mainProdTableInventory.setCellValueFactory(new PropertyValueFactory<>("prodStock"));
        mainProdTablePrice.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
    }

    public void onClickMainAddPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainModPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainAddProdBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProd.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onClickMainExitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onClickPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        String q = queryPartSearch.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
                if (p.getPartName().contains(q)) {
                    namedParts.add(p);
                }
                if (q.contains(String.valueOf(p.getPartID()))){
                    namedParts.add(p);
                }
                mainPartTable.setItems(namedParts);
                mainPartTable.refresh();
                // else "If part is not found, the application displays an error message in the UI or in a dialog box
            }
        }
        else {
            mainPartTable.setItems(allParts);
        }
    }
    public void onClickProdSearch(ActionEvent actionEvent)  {
        ObservableList<Product> namedProds = FXCollections.observableArrayList();
        ObservableList<Product> allProds = Inventory.getAllProducts();

        String q = queryProdSearch.getText();

        if (!q.isEmpty()) {
            for (Product p : allProds) {
                if (p.getProdName().contains(q)) {
                    namedProds.add(p);
                }
                if (q.contains(String.valueOf(p.getProdID()))){
                    namedProds.add(p);
                }
                mainProdTable.setItems(namedProds);
                mainProdTable.refresh();
                // else "If part is not found, the application displays an error message in the UI or in a dialog box
            }
        }
        else {
            mainProdTable.setItems(allProds);
        }
    }
}