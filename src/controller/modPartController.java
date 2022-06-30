package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.mainFormController.getModPartIndex;

public class modPartController implements Initializable {
    public RadioButton modPartInHouseBtn;
    public ToggleGroup addPartToggle;
    public RadioButton modPartOutsourcedBtn;
    public Label modPartMachineIDLabel;
    public Button modPartCancelBtn;
    public Button modPartSaveBtn;
    public TextField modPartID;
    public TextField modPartName;
    public TextField modPartStock;
    public TextField modPartPrice;
    public TextField modPartMax;
    public TextField modPartMachineOrCompany;
    public TextField modPartMin;

    private String exception = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onModPartInHouseBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Machine ID");
    }
    public void onModPartOutsourcedBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Company Name");
    }

    public void onModPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel modifying this part?");
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

    public void sendPart (int index, Part part) {
        modPartID.setText(String.valueOf(part.getPartID()));
        modPartName.setText(part.getPartName());
        modPartStock.setText(String.valueOf(part.getPartStock()));
        modPartPrice.setText(String.valueOf(part.getPartPrice()));
        modPartMax.setText(String.valueOf(part.getPartMax()));
        modPartMin.setText(String.valueOf(part.getPartMin()));

        if (part instanceof InHouse) {
            modPartInHouseBtn.setSelected(true);
            modPartMachineOrCompany.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else {
            modPartOutsourcedBtn.setSelected(true);
            modPartMachineOrCompany.setText(((Outsourced) part).getCompanyName());
        }
    }
    public void onModPartSaveBtn(ActionEvent actionEvent) throws IOException {
        String partID = modPartID.getText();
        String name = modPartName.getText();
        String stock = modPartStock.getText();
        String price = modPartPrice.getText();
        String min = modPartMin.getText();
        String max = modPartMax.getText();
        //int machineID;
        //String companyName;

        try {
            exception = Part.validPart(name, price, stock, min, max);
            if (exception != "") {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exception);
                alert.showAndWait();
            }

            else {
                if (modPartInHouseBtn.isSelected()){
                    InHouse inHousePart = new InHouse(Integer.parseInt(partID), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(modPartMachineOrCompany.getText()),true);
                    Inventory.updatePart(getModPartIndex(), inHousePart);
                }
                if (modPartOutsourcedBtn.isSelected()){
                    Outsourced outsourcedPart = new Outsourced(Integer.parseInt(partID), name, Double.parseDouble(price), Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), modPartMachineOrCompany.getText(),false);
                    Inventory.updatePart(getModPartIndex(), outsourcedPart);
                }
            }
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
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
}
