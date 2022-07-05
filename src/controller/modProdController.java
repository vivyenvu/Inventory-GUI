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
    private String exception = "";
    private ObservableList<Part> ascParts = FXCollections.observableArrayList();
    private int prodIndex = 0;

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
        try {
            Part part = (Part) modProdMainTable.getSelectionModel().getSelectedItem();
            ascParts.add(part);
            modProdAscPartTable.setItems(ascParts);
            //modProdAscPartTable.refresh();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part. ");
            alert.show();
        }
    }

    public void onModProdRemoveBtn(ActionEvent actionEvent) {
    }

    public void sendProd (int index, Product prod) {
        prodIndex = index;
        modProdID.setText(String.valueOf(prod.getProdID()));
        modProdName.setText(prod.getProdName());
        modProdStock.setText(String.valueOf(prod.getProdStock()));
        modProdPrice.setText(String.valueOf(prod.getProdPrice()));
        modProdMax.setText(String.valueOf(prod.getProdMax()));
        modProdMin.setText(String.valueOf(prod.getProdMin()));
        for (Part p : prod.getAllAssociatedParts()) {
            ascParts.addAssociatedParts(p);
        }
    }

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
                Product prod = new Product(prodID, name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max));
                Inventory.updateProduct(getModProdIndex(), prod);
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

    public void onClickModProdPartSearch(ActionEvent actionEvent) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = getAllParts();

        String q = queryModProdPartSearch.getText();

        if (!q.isEmpty()) {
            for (Part p : allParts) {
                if (p.getPartName().contains(q)) {
                    namedParts.add(p);
                }
                if (q.contains(String.valueOf(p.getPartID()))){
                    namedParts.add(p);
                }
                modProdMainTable.setItems(namedParts);
                modProdMainTable.refresh();
                // else "If part is not found, the application displays an error message in the UI or in a dialog box
            }
        }
        else {
            modProdMainTable.setItems(allParts);
        }
    }
}
