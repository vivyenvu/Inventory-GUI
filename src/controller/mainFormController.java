package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;

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

    private static Part modPart;
    private static int modPartIndex;
    private static Product modProd;
    private static int modProdIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainPartTable.setItems(getAllParts());

        mainPartTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        mainPartTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        mainPartTableInventory.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        mainPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        mainProdTable.setItems(getAllProducts());

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

    public static int getModPartIndex() {
        return modPartIndex;
    }
    public static int getModProdIndex() {
        return modProdIndex;
    }

    public void onClickMainModPartBtn(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modPart.fxml"));
            loader.load();

            modPart = (Part) mainPartTable.getSelectionModel().getSelectedItem();
            modPartIndex = getAllParts().indexOf(modPart);

            modPartController modPartCtrl = loader.getController();
            modPartCtrl.sendPart(modPartIndex, modPart);

            //Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //Scene scene = new Scene(root, 600, 600);
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part. ");
            alert.show();
        }
    }

    public void onClickMainDeletePartBtn(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            Part p = (Part) mainPartTable.getSelectionModel().getSelectedItem();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Part p = (Part) mainPartTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(p);
            }
            if (Inventory.getAllParts().contains(p)) {
                Alert delError = new Alert(Alert.AlertType.ERROR);
                delError.setTitle("Error");
                delError.setContentText("Part could not be deleted.");
            }
        }
        catch (NullPointerException e){
            Alert partsAsc = new Alert(Alert.AlertType.ERROR);
            partsAsc.setTitle("Error");
            partsAsc.setContentText("Can't delete because not part was selected.");
            partsAsc.show();
        }

    }

    public void onClickMainDeleteProdBtn(ActionEvent actionEvent) {
        Product prod = (Product) mainProdTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product p = (Product) mainProdTable.getSelectionModel().getSelectedItem();
            if (!p.getAllAssociatedParts().isEmpty()) {
                Alert partsAsc = new Alert(Alert.AlertType.ERROR);
                    partsAsc.setTitle("Error");
                    partsAsc.setContentText("Can't delete product that has parts associated.");
                    partsAsc.showAndWait();
                }
            else {
                Inventory.deleteProduct(p);

                if (Inventory.getAllProducts().contains(p)) {
                    Alert delError = new Alert(Alert.AlertType.ERROR);
                    delError.setTitle("Error");
                    delError.setContentText("Product could not be deleted.");
                }
            }
        }
    }

    public void onClickMainModProdBtn(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modProd.fxml"));
            loader.load();

            modProd = (Product) mainProdTable.getSelectionModel().getSelectedItem();
            modProdIndex = mainProdTable.getSelectionModel().getSelectedIndex();

            modProdController modProdCtrl = loader.getController();
            modProdCtrl.sendProd(modProdIndex, modProd);

            //Parent root = FXMLLoader.load(getClass().getResource("/view/modPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            //Scene scene = new Scene(root, 600, 600);
            Parent scene = loader.getRoot();
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a product. ");
            alert.show();
        }
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
        ObservableList<Part> allParts = getAllParts();

        String q = queryPartSearch.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
                //if you get to the end of q and can't find a part, then set the alert
                if (p.getPartName().contains(q)) {
                    namedParts.add(p);
                }
                if (q.contains(String.valueOf(p.getPartID()))){
                    namedParts.add(p);
                }
            }
            if (namedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Part not found.");
                alert.show();
                //mainPartTable.refresh();
            }
            mainPartTable.setItems(namedParts);
            mainPartTable.refresh();
        }
        else {
            mainPartTable.setItems(allParts);
        }
    }
    public void onClickProdSearch(ActionEvent actionEvent)  {
        ObservableList<Product> namedProds = FXCollections.observableArrayList();
        ObservableList<Product> allProds = getAllProducts();

        String q = queryProdSearch.getText();

        if (!q.isEmpty()) {
            for (Product p : allProds) {
                if (p.getProdName().contains(q)) {
                    namedProds.add(p);
                }
                if (q.contains(String.valueOf(p.getProdID()))) {
                    namedProds.add(p);
                }
            }
            if (namedProds.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Product not found.");
                alert.show();
            }
            mainProdTable.setItems(namedProds);
            mainProdTable.refresh();
        }
        else {
            mainProdTable.setItems(allProds);
        }
    }}