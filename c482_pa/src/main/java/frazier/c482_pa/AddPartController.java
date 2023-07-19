package frazier.c482_pa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** This class handles all user interaction on the Add Part screen*/
public class AddPartController {
    @FXML
    private RadioButton AddInhouseRbtn;

    @FXML
    private TextField AddMachineIdTxt;

    @FXML
    private RadioButton AddOutsourcedRbtn;

    @FXML
    private TextField AddPartInventoryTxt;

    @FXML
    private TextField AddPartMaxTxt;

    @FXML
    private TextField AddPartMinTxt;

    @FXML
    private TextField AddPartNameTxt;

    @FXML
    private TextField AddPartPriceTxt;

    @FXML
    private Label MachineIDorCompany;

    Stage stage;
    Parent scene;

    /** This method changes Company Name label to Machine ID if the Inhouse radio button is selected*/
    @FXML
    void OnActionAddPartInhouse(ActionEvent event) {
        MachineIDorCompany.setText("Machine ID");
    }

    /** This method changes the Machine ID label to Company Name if the Outsourced radio button is selected*/
    @FXML
    void onActionAddPartOutsourced(ActionEvent event) {
        MachineIDorCompany.setText("Company Name");
    }

    /** This method goes back to the main menu when the cancel button is clicked
     * @param event Action event that called the method (clicking the cancel button)*/
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method saves a new part object to the Inventory class.  The method retrieves various input values from the user and then checks
     * if the input values meet certain requirements and displays error messages if necessary.  If all input values are valid, it creates an InHouse or Outsourced
     * object based on the users selection and adds the Part object to the Inventory*/
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        try {
            String name = AddPartNameTxt.getText();
            int inventory = Integer.parseInt(AddPartInventoryTxt.getText());
            double price = Double.parseDouble(AddPartPriceTxt.getText());
            int min = Integer.parseInt(AddPartMinTxt.getText());
            int max = Integer.parseInt(AddPartMaxTxt.getText());

            int machineID;
            String companyName;

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            } else if (inventory < min || max < inventory) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between minimum and maximum.");
                alert.showAndWait();
                return;
            }

            if (AddInhouseRbtn.isSelected()){
                machineID = Integer.parseInt(AddMachineIdTxt.getText());
                InHouse addPart = new InHouse(name, price, inventory, min, max, machineID);
                Inventory.addPart(addPart);
            }

            if (AddOutsourcedRbtn.isSelected()){
                companyName = AddMachineIdTxt.getText();
                Outsourced addPart = new Outsourced(name, price, inventory, min, max, companyName);
                Inventory.addPart(addPart);
            }
            Stage stage = (Stage)((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }
}
