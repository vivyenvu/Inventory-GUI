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
import java.util.ResourceBundle;


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

    /**
     * Empty string to hold validation errors from Part.validPart() in the
     * onModPartSaveBtn method
     */
    private String exception = "";

    /**
     * Holds index of part being modified
     */
    private int modPartIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * When the radio button "In House" is selected, the bottom label will say "Machine ID"
     */
    public void onModPartInHouseBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Machine ID");
    }

    /**
     * When the radio button "Outsourced" is selected, the bottom label will say "Company Name"
     */
    public void onModPartOutsourcedBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Company Name");
    }

    /**
     * Cancel button transitions scene back to Main Menu
     */
    public void onModPartCancelBtn(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1080, 400);
            stage.setTitle("Back to Main Screen");
            stage.setScene(scene);
            stage.show();

    }

    /**
     * onClickMainModPartBtn in the mainFormController.java will call this method.
     * It takes data from that selected part to bring over to this screen so that
     * part's data can be modified
     * @param index is the index of the part in Inventory.getAllParts()
     * @param part is the part that was selected from the main screen
     */
    public void sendPart (int index, Part part) {
        modPartID.setText(String.valueOf(part.getPartID()));
        modPartName.setText(part.getPartName());
        modPartStock.setText(String.valueOf(part.getPartStock()));
        modPartPrice.setText(String.valueOf(part.getPartPrice()));
        modPartMax.setText(String.valueOf(part.getPartMax()));
        modPartMin.setText(String.valueOf(part.getPartMin()));
        modPartIndex = index;

        if (part instanceof InHouse) {
            modPartInHouseBtn.setSelected(true);
            modPartMachineOrCompany.setText(String.valueOf(((InHouse) part).getMachineID()));
            modPartMachineIDLabel.setText("Machine ID");
        }
        else {
            modPartOutsourcedBtn.setSelected(true);
            modPartMachineOrCompany.setText(((Outsourced) part).getCompanyName());
            modPartMachineIDLabel.setText("Company Name");
        }
    }

    /**
     * Save button will take the data in the fields, validate them, and determine
     * if the part is In House or Outsourced. If all data is present and valid, a new part will be
     * made and added to the Inventory.allParts List. Otherwise, descriptive error messages will pop up.
     * After completion, scene transitions to the Main screen
     */
    public void onModPartSaveBtn(ActionEvent actionEvent) throws IOException {
        String partID = modPartID.getText();
        String name = modPartName.getText();
        String stock = modPartStock.getText();
        String price = modPartPrice.getText();
        String min = modPartMin.getText();
        String max = modPartMax.getText();
        String machOrComp = modPartMachineOrCompany.getText();
        boolean madeInHouse = true;
        if (modPartInHouseBtn.isSelected()) {
            madeInHouse = true;
        }
        else if (modPartOutsourcedBtn.isSelected()){
            madeInHouse = false;
        }

        try {
            exception = Part.validPart(name, price, stock, min, max, machOrComp, madeInHouse);
            if (exception != "") {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exception);
                alert.showAndWait();
            }

            else {
                double roundedPrice = (Math.round(Double.parseDouble(price)*100))/100.0;
                if (modPartInHouseBtn.isSelected()){
                    InHouse inHousePart = new InHouse(Integer.parseInt(partID), name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(modPartMachineOrCompany.getText()),true);
                    Inventory.updatePart(modPartIndex, inHousePart);
                }
                else if (modPartOutsourcedBtn.isSelected()){
                    Outsourced outsourcedPart = new Outsourced(Integer.parseInt(partID), name, roundedPrice, Integer.parseInt(stock), Integer.parseInt(min), Integer.parseInt(max), modPartMachineOrCompany.getText(),false);
                    Inventory.updatePart(modPartIndex, outsourcedPart);
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
