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

import static controller.mainFormController.getModPartIndex;
import static controller.mainFormController.getModProdIndex;
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
    public TextField modProdName;
    public TextField modProdStock;
    public TextField modProdPrice;
    public TextField modProdMax;
    public TextField modProdMin;
    public TextField modProdID;
    public TextField queryModProdPartSearch;

    /**
     * Empty string to hold validation errors from Product.validProd() in the
     * onModProdSaveBtn method
     */
    private String exception = "";
    List<Part> partsList = new ArrayList<Part>();
    private ObservableList<Part> ascPartsDisplay = FXCollections.observableList(partsList);

    /**
     * Index of current product that was selected from the mainFormController's method onClickMainModProdBtn
     */
    //private int prodIndex = 0;

    /**
     * Reference to product that was selected from the mainFormController's method onClickMainModProdBtn
     */
    private Product currentProd;

    /**
     * Start the scene by populating the top table with all the parts in the Inventory
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modProdMainTable.setItems(getAllParts());

        modProdMainTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modProdMainTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modProdMainTableStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modProdMainTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));

        //modProdAscPartTable.getItems().clear();
    }

    /**
     * When you hit Cancel, you'll be redirected to the Main Menu
     */
    public void onModProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When you hit Add, the selected part will be added to the product's List of
     * associated parts and the bottom table will reflect this addition.
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
     * When you press the Remove Associated Part button, a confirmation will pop up. If you press OK,
     * that part will be removed from the associated parts List and the bottom table will update to
     * reflect this deletion.
     */
    public void onModProdRemoveBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Part p = (Part) modProdAscPartTable.getSelectionModel().getSelectedItem();
                if (currentProd.getAllAssociatedParts().contains(p)) {
                    currentProd.deleteAssociatedPart(p);
                    modProdAscPartTable.setItems(currentProd.getAllAssociatedParts());
                }
            } catch (NullPointerException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setContentText("Please select a part. ");
                err.show();
            }
        }
    }

    /**
     * onClickMainModProdBtn in the mainFormController.java will call this method.
     * It takes data from that selected product to bring over to this screen, so you
     * can modify that product's information.It will also display that product's
     * associated parts in the bottom table
     */
    public void sendProd (int index, Product prod) {
        ascPartsDisplay.addAll(prod.getAllAssociatedParts());
        currentProd = prod;
        //prodIndex = index;
        modProdID.setText(String.valueOf(currentProd.getProdID()));
        modProdName.setText(currentProd.getProdName());
        modProdStock.setText(String.valueOf(currentProd.getProdStock()));
        modProdPrice.setText(String.valueOf(currentProd.getProdPrice()));
        modProdMax.setText(String.valueOf(currentProd.getProdMax()));
        modProdMin.setText(String.valueOf(currentProd.getProdMin()));

        modProdAscPartTable.getItems().clear();
        modProdAscPartTable.setItems(ascPartsDisplay);

        modProdAscPartTableID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modProdAscPartTableName.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modProdAscPartTableStock.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modProdAscPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        modProdAscPartTable.refresh();

    }

    /**
     * When you press the Save button, validProd will be called to validate all the data entered into the text fields
     * If all the data is valid, then a new Product will be created to updated the currentProd at
     * prodIndex in the Inventory.allProducts List.
     * Otherwise, error messages will pop up. After completion, you are redirected back to the Main screen
     */
    public void onModProdSaveBtn(ActionEvent actionEvent) throws IOException{
        int prodID = Integer.parseInt(modProdID.getText());
        String name = modProdName.getText();
        String stock = modProdStock.getText();
        String price = modProdPrice.getText();
        String min = modProdMin.getText();
        String max = modProdMax.getText();

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
                currentProd.setProdID(prodID);
                currentProd.setProdName(name);
                currentProd.setProdPrice(roundedPrice);
                currentProd.setProdStock(Integer.parseInt(stock));
                currentProd.setProdMin(Integer.parseInt(min));
                currentProd.setProdMax(Integer.parseInt(max));
                currentProd.setProdParts(ascPartsDisplay);


                //prod.setProdParts(currentProd.getAllAssociatedParts());
                //prod.setProdParts(modProdAscPartTable.getItems());
                //Inventory.updateProduct(prodIndex, prod); // or getModProdIndex()
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
        //clear variables
        ascPartsDisplay = null;
        modProdAscPartTable.setItems(ascPartsDisplay);
        modProdAscPartTable.refresh();
        currentProd = null;
        exception = "";

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
    public void onClickModProdPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryModProdPartSearch.getText();

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
            modProdMainTable.setItems(namedParts);
            modProdMainTable.refresh();
        }
        else {
            modProdMainTable.setItems(allParts);
        }
    }
}
