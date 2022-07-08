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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.getAllParts;

public class addProdController implements Initializable {
    public Button addProdAddBtn;
    public Button addProdRemoveBtn;
    public TextField queryPartSearchProd;
    public TableView prodPartMainTable;
    public TableColumn prodPartMainTableID;
    public TableColumn prodPartMainTableName;
    public TableColumn prodPartMainTableStock;
    public TableColumn prodPartMainTablePrice;
    public TextField prodName;
    public TextField prodStock;
    public TextField prodPrice;
    public TextField prodMax;
    public TextField prodMin;
    public TextField prodID;
    public TableView prodAscPartTable;
    public TableColumn ascPartID;
    public TableColumn ascPartName;
    public TableColumn ascPartStock;
    public TableColumn ascPartPrice;

    /**
     * Empty string to hold validation errors from Product.validProd() in the
     * onAddProdSaveBtn method
     */
    private String exception = "";

    /**
     * List of associated parts with each product
     */
    List<Part> partsList = new ArrayList<Part>();
    private ObservableList<Part> ascParts = FXCollections.observableList(partsList);

    /**
     * Populate the top table with all the Parts in the inventory
     * Populate the bottom table with all the associated parts to that Product
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodPartMainTable.setItems(getAllParts());

        prodPartMainTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        prodPartMainTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        prodPartMainTableStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        prodPartMainTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        prodAscPartTable.setItems(ascParts);

        ascPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        ascPartName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        ascPartStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        ascPartPrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }

    /**
     * Hitting the Cancel button will redirect you to the main menu
     */
    public void onAddProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When you hit Add, the selected part will be added to the product's List of
     * associated parts (ascParts) and the bottom table will reflect this addition.
     */
    public void onAddProdAddPartBtn(ActionEvent actionEvent) {
            Part part = (Part) prodPartMainTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error");
            err.setContentText("Please select a part. ");
            err.show();
        }
        else {
            ascParts.add(part);
            prodAscPartTable.setItems(ascParts);
            prodAscPartTable.refresh();
        }
    }

    /**
     * When you press the Remove Associated Part button, a confirmation will pop up. If you press OK,
     * that part will be removed from the associated parts List and the bottom table will update to
     * reflect this deletion.
     */
    public void onAddProdRemoveBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
                Part p = (Part) prodAscPartTable.getSelectionModel().getSelectedItem();
                if (ascParts.contains(p)) {
                    //deleteAssociatedPart(p);
                    ascParts.remove(p);
                    prodAscPartTable.setItems(ascParts);
                }
        }
    }

    /**
     * When you press the Save button, validProd will be called to validate all the data entered into the text fields
     * If all the data is valid, then a new Product will be created and added to the Inventory.allProducts List.
     * Otherwise, error messages will pop up. After completion, you are redirected back to the Main screen
     */
    public void onAddProdSaveBtn(ActionEvent actionEvent) throws IOException{
        int prodID = Inventory.getAllProducts().size() + 9;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getProdID() == prodID) {
                prodID ++;
                i=0;
            }
        }
        String name = prodName.getText();
        String stock = prodStock.getText();
        String price = prodPrice.getText();
        String min = prodMin.getText();
        String max = prodMax.getText();

        try {
            exception = Product.validProd(name, price, stock, min, max);
            if (exception != "") {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exception);
                alert.showAndWait();
            }

            else {
                double roundedPrice = (Math.round(Double.parseDouble(price)*100))/100.0;
                Product prod = new Product(prodID, name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max));
                prod.setProdParts(ascParts);
                Inventory.addProduct(prod);
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Takes partial name or partial id to search for the part in the Inventory.allParts List
     * An error pops up if part is not found
     * Table is refreshed to display what parts match the partial search
     * If search field is empty, the table displays all the parts in the Inventory
     */
    public void onClickPartSearchProd(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryPartSearchProd.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
                if (p.getPartName().contains(q)) {
                    namedParts.add(p);
                }
                if (q.contains(String.valueOf(p.getPartID()))) {
                    namedParts.add(p);
                }
            }
            if (namedParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Part not found.");
                alert.show();
            }
            prodPartMainTable.setItems(namedParts);
            prodPartMainTable.refresh();
        }
        else {
            prodPartMainTable.setItems(allParts);
        }
    }
}
