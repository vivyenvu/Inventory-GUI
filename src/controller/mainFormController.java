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

    /**
     * Holds part to be sent to Modify Part Scene using sendPart method
     */
    private static Part modPart;

    /**
     * Holds part index to be sent to Modify Part Scene using sendPart method
     */
    private static int modPartIndex;

    /**
     * Holds product to be sent to Modify Product Scene using sendProd method
     */
    private static Product modProd;

    /**
     * Holds product index to be sent to Modify Product Scene using sendProd method
     */
    private static int modProdIndex;

    /**
     * Starts the scene by populating the Part and Product Table with the test data
     */
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

    /**
     * When you click "Add" on the Part side, it'll redirect you to the Add Part menu
     */
    public void onClickMainAddPartBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gives you the index of the part that you're modifying
     */
    public static int getModPartIndex() {
        return modPartIndex;
    }

    /**
     * Gives you the index of the product that you're modifying
     */
    public static int getModProdIndex() {
        return modProdIndex;
    }

    /**
     * When you click "Modify" on the Part side, it'll call the sendPart method to take
     * the data of the selected part, and send it to the Modify Part scene, as well as
     * redirect the page to that scene. If you didn't select a part, an error will pop up.
     */
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

    /**
     * Removes part from inventory and updates the part table to show that it has been deleted.
     * Confirmation pop up to make sure user wants to delete part, and error pop up if
     * part cannot be deleted
     */
    public void onClickMainDeletePartBtn(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setContentText("Are you sure you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            Part p = (Part) mainPartTable.getSelectionModel().getSelectedItem();
            if (result.isPresent() && result.get() == ButtonType.OK) {
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
            partsAsc.setContentText("Can't delete because no part was selected.");
            partsAsc.show();
        }

    }

    /**
     * Removes product from inventory and updates the part table to show that it has been deleted.
     * Confirmation pop up to make sure user wants to delete product, and error pop up if
     * product cannot be deleted
     */
    public void onClickMainDeleteProdBtn(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            Product prod = (Product) mainProdTable.getSelectionModel().getSelectedItem();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (!prod.getAllAssociatedParts().isEmpty()) {
                    Alert partsAsc = new Alert(Alert.AlertType.ERROR);
                    partsAsc.setTitle("Error");
                    partsAsc.setContentText("Can't delete product that has parts associated.");
                    partsAsc.showAndWait();
                }
                else {
                    Inventory.deleteProduct(prod);
                }
                    if (Inventory.getAllProducts().contains(prod)) {
                        Alert delError = new Alert(Alert.AlertType.ERROR);
                        delError.setTitle("Error");
                        delError.setContentText("Product could not be deleted.");
                }
            }
        } catch (NullPointerException e) {
            Alert notSelected = new Alert(Alert.AlertType.ERROR);
            notSelected.setTitle("Error");
            notSelected.setContentText("Please select a product");
            notSelected.show();
        }
    }


    /**
     * When you click the Modify button on the Product side, it'll call the sendProd method to take
     * the data of the selected product, and send it to the Modify Product scene, as well as
     * redirect the page to that scene. If you didn't select a product, an error will pop up.
     */
    public void onClickMainModProdBtn(ActionEvent actionEvent) throws IOException {
        /**
         * RUNTIME ERROR: If a Product was not selected, but the user clicked on the Modify
         * button on the Product side, this would invoke a NullPointerException. The solution implemented
         * is catching that error so a popup occurs, rather than it crashing the program.
         */
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/modProd.fxml"));
            loader.load();

            modProd = (Product) mainProdTable.getSelectionModel().getSelectedItem();
            modProdIndex = mainProdTable.getSelectionModel().getSelectedIndex();

            modProdController modProdCtrl = loader.getController();
            modProdCtrl.sendProd(modProdIndex, modProd);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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

    /**
     * Redirects you to the Add Product menu
     */
    public void onClickMainAddProdBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProd.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes the whole stage, ending the application
     */
    public void onClickMainExitBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Takes partial name or partial id to search for the part in the Inventory.allParts List
     * An error pops up if part is not found
     * Table is refreshed to display what parts are around based off partial search
     * If search field is empty, the table displays all of the parts in the Inventory
     */
    public void onClickPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryPartSearch.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
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

    /**
     * Takes partial name or partial id to search for the product in the Inventory.allProds List
     * An error pops up if product is not found
     * Table is refreshed to display what products are around based off partial search
     * If search field is empty, the table displays all of the products in the Inventory
     */
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