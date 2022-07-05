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
    private String exception = "";
    private ObservableList<Part> ascParts = FXCollections.observableArrayList();

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

    public void onAddProdCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddProdAddPartBtn(ActionEvent actionEvent) {
        try {
            Part part = (Part) prodPartMainTable.getSelectionModel().getSelectedItem();
            ascParts.add(part);
            prodAscPartTable.setItems(ascParts);
            //prodAscPartTable.refresh();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a part. ");
            alert.show();
        }
    }

    public void onAddProdRemoveBtn(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Products");
        alert.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Part p = (Part) prodAscPartTable.getSelectionModel().getSelectedItem();
                if (ascParts.contains(p)) {
                    Product.deleteAssociatedPart(p);
                    ascParts.remove(p);
                    prodAscPartTable.setItems(ascParts);
                }
            } catch (NullPointerException e) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Error");
                err.setContentText("Please select a part. ");
                err.show();
            }
        }
    }

    public void onAddProdSaveBtn(ActionEvent actionEvent) throws IOException{
        int prodID =(int)(Math.random() * 100);
        for (int i = 0; i < Inventory.getAllProducts().size(); i++) {
            if (Inventory.getAllParts().get(i).getPartID() == prodID) {
                prodID = (int)(Math.random() * 100);
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
                Product prod = new Product(prodID, name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max));
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
