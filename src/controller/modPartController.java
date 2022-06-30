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

    private String exception = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 500);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onModPartInHouseBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Machine ID");
        //THIS CHANGES THE NAME BUT NOT THE INPUT FIELD. POSSIBLE SOLUTION IS TO WRITE AND IF...THEN STATEMENT SO DATA IS ASSIGNED TO MACHINE VS COMPANY VARIABLE
    }
    public void onModPartOutsourcedBtn(ActionEvent actionEvent) {
        modPartMachineIDLabel.setText("Company Name");
        //ALSO NEED TO CHANGE THE INPUT FIELD TO ACCEPT DATA FOR COMPANY NAME INSTEAD
    }

    public void onModPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void sendPart (Part part) {
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
        int partID = Integer.parseInt(modPartID.getText());
        String name = modPartName.getText();
        int stock = Integer.parseInt(modPartStock.getText());
        double price = Double.parseDouble(modPartPrice.getText());
        int min = Integer.parseInt(modPartMin.getText());
        int max = Integer.parseInt(modPartMax.getText());
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
                    InHouse inHousePart = new InHouse(partID, name, price, stock, min, max, Integer.parseInt(modPartMachineOrCompany.getText()),true);
                    Inventory.updatePart(/*I NEED INDEX*/ 10, inHousePart);
                }
                if (modPartOutsourcedBtn.isSelected()){
                    Outsourced outsourcedPart = new Outsourced(partID, name, price, stock, min, max, modPartMachineOrCompany.getText(), false);
                    Inventory.updatePart(/*I NEED INDEX*/ 10, outsourcedPart);
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
