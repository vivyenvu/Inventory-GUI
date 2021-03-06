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

/**
 * Allows user to add a new product. Includes fields name, inventory, price, min, max, and tables
 * to select parts to associate.
 */
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
    public Label errorMessagesDisplay;

    /**
     * Empty string to hold validation errors from Product.validProd() in the
     * onAddProdSaveBtn method.
     */
    private String exception = "";

    /**
     * List of associated parts with each product.
     */
    List<Part> partsList = new ArrayList<Part>();
    private ObservableList<Part> ascParts = FXCollections.observableList(partsList);

    /**
     * Populates the top table with all the Parts in the inventory.
     * Populates the bottom table with all the associated parts to that Product.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prodPartMainTable.setItems(getAllParts());

        prodPartMainTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodPartMainTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPartMainTableStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPartMainTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodAscPartTable.setItems(ascParts);

        ascPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ascPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ascPartStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ascPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Cancel button changes scene to Main menu.
     */
    public void onAddProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 400);
            stage.setTitle("Back to Main Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Add button adds selected part to the bottom table.
     */
    public void onAddProdAddPartBtn(ActionEvent actionEvent) {
        Part part = (Part) prodPartMainTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert err = new Alert(Alert.AlertType.ERROR);
            err.setTitle("Error");
            err.setContentText("Please select a part. ");
            err.show();
        } else {
            ascParts.add(part);
            prodAscPartTable.setItems(ascParts);
            prodAscPartTable.refresh();
        }
    }

    /**
     * Remove Associated Part button will remove that part from the bottom table.
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
     * Save button will call validProd to validate all the data entered into the text fields.
     * If all the data is valid, then a new Product will be created with its own List of associated parts, and
     * that will be added to the Inventory.allProducts List.
     * Otherwise, error messages will pop up. After completion, scene transitions back to the Main screen.
     */
    public void onAddProdSaveBtn(ActionEvent actionEvent) throws IOException {
        int prodID = Inventory.getAllProducts().size() + 9;
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllProducts().get(i).getProdID() == prodID) {
                prodID++;
                i = 0;
            }
        }
        String name = prodName.getText();
        String stock = prodStock.getText();
        String price = prodPrice.getText();
        String min = prodMin.getText();
        String max = prodMax.getText();

        String errorMessages = "";

        if (name.isEmpty()) {
            errorMessages += "Name field is required. \n";
        }
        try {
            int validatedStock = Integer.parseInt(stock);
        } catch (NumberFormatException e) {
            errorMessages += "Inventory must be an integer. \n";
        }
        try {
            Double validatedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            errorMessages += "Price must be a double. \n";
        }
        try {
            int validatedMax = Integer.parseInt(max);
        } catch (NumberFormatException e) {
            errorMessages += "Max must be an integer. \n";
        }

        try {
            int validatedMin = Integer.parseInt(min);
        } catch (NumberFormatException e) {
            errorMessages += "Min must be an integer. \n";
        }

        if (errorMessages != "") {
            errorMessagesDisplay.setText(errorMessages);
        }
        else {
            errorMessages = "";
            int validatedStock = Integer.parseInt(stock);
            int validatedMin = Integer.parseInt(min);
            int validatedMax = Integer.parseInt(max);
            Double validatedPrice = Double.parseDouble(price);

            if (validatedMin > validatedMax) {
                errorMessages += "Min must be less than max. \n";
            }
            if (validatedStock < validatedMin || validatedStock > validatedMax) {
                errorMessages += "Stock must be between min and max values. \n";
            }
            if (validatedStock < 1) {
                errorMessages += "Stock must be greater than 0. \n";
            }
            if (validatedPrice < 0) {
                errorMessages += "Price must be greater than 0. \n";
            }

        if (errorMessages != "") {
            errorMessagesDisplay.setText("");
            errorMessagesDisplay.setText(errorMessages);
        }
        else {
            double roundedPrice = (Math.round(Double.parseDouble(price) * 100)) / 100.0;
            Product prod = new Product(prodID, name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max));
            prod.setProdParts(ascParts);
            Inventory.addProduct(prod);

            Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 400);
            stage.setTitle("Back to Main Screen");
            stage.setScene(scene);
            stage.show();
            }
        }
    }

    /**
     * Takes partial name or partial id to search for the part in the Inventory.allParts List.
     * An error pops up if part is not found.
     * Table is refreshed to display what parts match the partial search.
     * If search field is empty, the table displays all the parts in the Inventory.
     */
    public void onClickPartSearchProd(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryPartSearchProd.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
                if (p.getName().contains(q)) {
                    namedParts.add(p);
                }
                if (q.contains(String.valueOf(p.getId()))) {
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
