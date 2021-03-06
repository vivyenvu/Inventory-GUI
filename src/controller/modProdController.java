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
 * Allows user to modify an existing product.
 */
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
    public TextField modProdName;
    public TextField modProdStock;
    public TextField modProdPrice;
    public TextField modProdMax;
    public TextField modProdMin;
    public TextField modProdID;
    public TextField queryModProdPartSearch;
    public Label errorMessagesDisplay;

    /**
     * Empty string to hold validation errors from Product.validProd() in the
     * onModProdSaveBtn method.
     */
    private String exception = "";

    /**
     * List of associated parts with each product.
     */
    List<Part> partsList = new ArrayList<Part>();
    private ObservableList<Part> ascPartsDisplay = FXCollections.observableList(partsList);

    /**
     * Reference to product that was selected from the mainFormController's method onClickMainModProdBtn.
     */
    private Product currentProd;

    /**
     * Start the scene by populating the top table with all the parts in the Inventory.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdMainTable.setItems(getAllParts());

        modProdMainTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdMainTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdMainTableStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdMainTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //modProdAscPartTable.getItems().clear();
    }

    /**
     * Cancel button will transition scene to the Main Menu.
     */
    public void onModProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying this product?");
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
    public void onModProdAddBtn(ActionEvent actionEvent) {
            Part part = (Part) modProdMainTable.getSelectionModel().getSelectedItem();
            if (part == null) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setContentText("Please select a part. ");
                err.show();
            }
            else {
                ascPartsDisplay.add(part);
                modProdAscPartTable.setItems(ascPartsDisplay);
                modProdAscPartTable.refresh();
            }
            /*
            Debugging
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setContentText(Integer.toHexString(System.identityHashCode(currentProd.getAllAssociatedParts())));
            alert1.showAndWait();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(Integer.toString(currentProd.getAllAssociatedParts().size()));
            alert.showAndWait();

            Alert aler = new Alert(Alert.AlertType.INFORMATION);
            aler.setContentText(currentProd.getProdName());
            aler.showAndWait();*/
    }

    /**
     * Remove Associated Parts button will remove the selected part from the bottom table.
     */
    public void onModProdRemoveBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            List<Part> displayList = new ArrayList<Part>();
            ObservableList<Part> deleteDisplayList = FXCollections.observableList(displayList);
            deleteDisplayList.addAll(currentProd.getAllAssociatedParts());

            Part p = (Part) modProdAscPartTable.getSelectionModel().getSelectedItem();
                if (currentProd.getAllAssociatedParts().contains(p)) {
                    Product display = new Product (currentProd.getProdID(), currentProd.getProdName(), currentProd.getProdPrice(), currentProd.getProdStock(), currentProd.getProdMin(), currentProd.getProdMax());
                    display.setProdParts(deleteDisplayList);
                    display.deleteAssociatedPart(p);
                    //currentProd.deleteAssociatedPart(p);

                    ascPartsDisplay = display.getAllAssociatedParts();
                    //ascPartsDisplay.addAll(currentProd.getAllAssociatedParts());
                    modProdAscPartTable.setItems(ascPartsDisplay);
                }
        }
    }

    /**
     * onClickMainModProdBtn in the mainFormController.java will call this method.
     * It takes data from that selected product to bring over to this screen so its
     * data can be modified. That product's associated parts will be displayed in the bottom table
     * @param prod the product that was selected from the main screen
     */
    public void sendProd (Product prod) {
        ascPartsDisplay.addAll(prod.getAllAssociatedParts());
        currentProd = prod;
        modProdID.setText(String.valueOf(currentProd.getProdID()));
        modProdName.setText(currentProd.getProdName());
        modProdStock.setText(String.valueOf(currentProd.getProdStock()));
        modProdPrice.setText(String.valueOf(currentProd.getProdPrice()));
        modProdMax.setText(String.valueOf(currentProd.getProdMax()));
        modProdMin.setText(String.valueOf(currentProd.getProdMin()));

        modProdAscPartTable.getItems().clear();
        modProdAscPartTable.setItems(ascPartsDisplay);

        modProdAscPartTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProdAscPartTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        modProdAscPartTableStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modProdAscPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        modProdAscPartTable.refresh();
    }

    /**
     * Save button calls validProd to validate all the data entered into the text fields.
     * If all the data is valid, then that product's information will be updated.
     * Otherwise, error messages will pop up. After completion, you are redirected back to the Main screen.
     */
    public void onModProdSaveBtn(ActionEvent actionEvent) throws IOException {
        int prodID = Integer.parseInt(modProdID.getText());
        String name = modProdName.getText();
        String stock = modProdStock.getText();
        String price = modProdPrice.getText();
        String min = modProdMin.getText();
        String max = modProdMax.getText();

        String errorMessages = "";

        if (name.isEmpty()) {
            errorMessages += "Name field is required. \n";
        }
        try {
            int validatedStock = Integer.parseInt(stock);
        }
        catch (NumberFormatException e) {
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
                currentProd.setProdID(prodID);
                currentProd.setProdName(name);
                currentProd.setProdPrice(roundedPrice);
                currentProd.setProdStock(Integer.parseInt(stock));
                currentProd.setProdMin(Integer.parseInt(min));
                currentProd.setProdMax(Integer.parseInt(max));
                currentProd.setProdParts(ascPartsDisplay);

                //clear variables
                ascPartsDisplay = null;
                modProdAscPartTable.setItems(ascPartsDisplay);
                modProdAscPartTable.refresh();
                currentProd = null;
                exception = "";

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
    public void onClickModProdPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryModProdPartSearch.getText();

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
            modProdMainTable.setItems(namedParts);
            modProdMainTable.refresh();
        }
        else {
            modProdMainTable.setItems(allParts);
        }
    }
}
