package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class modPartController implements Initializable {
    public RadioButton modPartInHouseBtn;
    public ToggleGroup addPartToggle;
    public RadioButton modPartOutsourcedBtn;
    public TextField modPartMachineIDInput;
    public Label modPartMachineIDLabel;
    public Button modPartCancelBtn;
    public Button modPartSaveBtn;

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
            inHouseBtn.setSelected(true);
            modPartSourceTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else {
            outsourcedBtn.setSelected(true);
            modPartSourceTxt.setText(((Outsource) part).getCompanyName());
        }
    }
    public void onModPartSaveBtn(ActionEvent actionEvent) throws IOException {
        //how to bring selected part from Main form to this modify form

        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 400);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }
}
