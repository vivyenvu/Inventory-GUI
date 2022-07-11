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

/**
 * Allows user to add a new part. Includes fields name, inventory, price, max, min, and machine id or company name.
 */
public class addPartController implements Initializable {
    public ToggleGroup addPartToggle;
    public TextField addPartMachineID;
    public Label addPartMachineIDLabel;
    public Button addPartCancelBtn;
    public Button addPartSaveBtn;
    public RadioButton addPartInHouseBtn;
    public RadioButton addPartOutsourcedBtn;
    public TextField addPartName;
    public TextField addPartStock;
    public TextField addPartPrice;
    public TextField addPartMax;
    public TextField addPartMin;
    public Label errorMessagesDisplay;

    /**
     * Empty string to hold validation errors from Part.validPart() in the
     * onAddPartSaveBtn method.
     */
    private String exception = "";

    /**
     * Does nothing because scene is set up by mainFormController
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Cancel button changes scene to Main Menu.
     */
    public void addPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure you want to cancel adding a new part?");
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
     * When the radio button "In House" is selected, the bottom label will say "Machine ID".
     */
    public void onAddPartInHouseBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
    }

    /**
     * When the radio button "Outsourced" is selected, the bottom label will say "Company Name".
     */
    public void onAddPartOutsourcedBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
    }

    /**
     * Save button will take the data in the fields, validate them, and determine
     * if the part is In House or Outsourced. If all data is present and valid, a new part will be
     * made and added to the Inventory.allParts List. Otherwise, descriptive error messages will pop up.
     * After completion, scene transitions the Main screen.
     */
    public void onAddPartSaveBtn(ActionEvent actionEvent) throws IOException {
        int partID = Inventory.getAllParts().size() + 1;
        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            if (Inventory.getAllParts().get(i).getId() == partID) {
                partID++;
                i = 0;
            }
        }
        String name = addPartName.getText();
        String stock = addPartStock.getText();
        String price = addPartPrice.getText();
        String min = addPartMin.getText();
        String max = addPartMax.getText();
        String machOrComp = addPartMachineID.getText();

        String errorMessages = "";

        if (name.isEmpty()){
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
        }
        catch (NumberFormatException e) {
            errorMessages += "Price must be a double. \n";
        }
        try {
            int validatedMax = Integer.parseInt(max);
        }
        catch (NumberFormatException e) {
            errorMessages += "Max must be an integer. \n";
        }

        try {
            int validatedMin = Integer.parseInt(min);
        }
        catch (NumberFormatException e) {
            errorMessages += "Min must be an integer. \n";
        }

        if (addPartInHouseBtn.isSelected()) {
            try {
                int validatedMachineID = Integer.parseInt(machOrComp);
            }
            catch (NumberFormatException e) {
                errorMessages += "Machine ID must be an integer. \n";
            }
        }

        if (errorMessages != ""){
            errorMessagesDisplay.setText(errorMessages);
        }
        else {
            errorMessages = "";
            int validatedStock =  Integer.parseInt(stock);
            int validatedMin = Integer.parseInt(min);
            int validatedMax = Integer.parseInt(max);
            Double validatedPrice = Double.parseDouble(price);

            if (validatedMin > validatedMax) {
                errorMessages += "Min must be less than max. \n";
            }
            if (validatedStock < validatedMin || validatedStock > validatedMax) {
                errorMessages += "Stock must be between min and max values. \n";
            }
            if (validatedStock < 1){
                errorMessages += "Stock must be greater than 0. \n";
            }
            if (validatedPrice < 0){
                errorMessages += "Price must be greater than 0. \n";
            }

            if (errorMessages != ""){
                errorMessagesDisplay.setText("");
                errorMessagesDisplay.setText(errorMessages);
            }
            else{
                double roundedPrice = (Math.round(Double.parseDouble(price) * 100)) / 100.0;
                if (addPartInHouseBtn.isSelected()) {
                    int validatedMachineID = Integer.parseInt(machOrComp);
                    InHouse inHousePart = new InHouse(partID, name, roundedPrice, validatedStock, validatedMin, validatedMax, validatedMachineID);
                    Inventory.addPart(inHousePart);

                } else if (addPartOutsourcedBtn.isSelected()) {
                    Outsourced outsourcedPart = new Outsourced(partID, name, roundedPrice, validatedStock, validatedMin, validatedMax, machOrComp);
                    Inventory.addPart(outsourcedPart);
                }

                Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1080, 400);
                stage.setTitle("Back to Main Screen");
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
