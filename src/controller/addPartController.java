package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class addPartController implements Initializable {
    public ToggleGroup addPartToggle;
    public TextField addPartMachineIDInput;
    public Label addPartMachineIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addPartCancelBtn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1080, 500);
        stage.setTitle("Back to Main Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddPartInHouseBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
        //THIS CHANGES THE NAME BUT NOT THE INPUT FIELD. POSSIBLE SOLUTION IS TO WRITE AND IF...THEN STATEMENT SO DATA IS ASSIGNED TO MACHINE VS COMPANY VARIABLE
    }

    public void onAddPartOutsourcedBtn(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
        //ALSO NEED TO CHANGE THE INPUT FIELD TO ACCEPT DATA FOR COMPANY NAME INSTEAD
    }
}
