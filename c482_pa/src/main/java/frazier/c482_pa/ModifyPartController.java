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

/** This class handles all user interaction on the Modify Part screen*/
public class ModifyPartController {
    @FXML
    private Label MachineIDorCompany;

    @FXML
    private RadioButton ModifyInhouseRbtn;

    @FXML
    private TextField ModifyInventoryTxt;

    @FXML
    private TextField ModifyMachineTxt;

    @FXML
    private TextField ModifyMaxTxt;
    @FXML
    private TextField ModifyPartID;


    @FXML
    private TextField ModifyMinTxt;

    @FXML
    private RadioButton ModifyOutsourcedRbtn;

    @FXML
    private TextField ModifyPartNameTxt;

    @FXML
    private TextField ModifyPriceTxt;

    Stage stage;
    Parent scene;
    private int currentIndex = 0;


    /** This method goes back to the main menu when the cancel button is clicked
     * @param event Action event that called the method (clicking the cancel button)*/
    @FXML
    void onActionBackToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method updates the Modify Part screen with the details of the selected part
     * @param selectedIndex The index of the selected part in the allParts ObservableList
     * @param part The selected part object*/
    public void matchPart(int selectedIndex, Part part){
        currentIndex = selectedIndex;

        if (part instanceof InHouse){
            ModifyInhouseRbtn.setSelected(true);
            ModifyMachineTxt.setText(String.valueOf(((InHouse)part).getMachineID()));
            MachineIDorCompany.setText("Machine ID");
        }
        else {
            ModifyOutsourcedRbtn.setSelected(true);
            ModifyMachineTxt.setText(String.valueOf(((Outsourced)part).getCompanyName()));
            MachineIDorCompany.setText("Company Name");
        }

        ModifyPartID.setText(String.valueOf(part.getId()));
        ModifyPartNameTxt.setText(String.valueOf(part.getName()));
        ModifyInventoryTxt.setText(String.valueOf(part.getStock()));
        ModifyPriceTxt.setText(String.valueOf(part.getPrice()));
        ModifyMaxTxt.setText(String.valueOf(part.getMax()));
        ModifyMinTxt.setText(String.valueOf(part.getMin()));
    }

    /** This method changes Company Name label to Machine ID if the Inhouse radio button is selected*/
    @FXML
    void onActionModifyPartInhouse(ActionEvent event) {
        MachineIDorCompany.setText("Machine ID");
    }

    /** This method changes the Machine ID label to Company Name if the Outsourced radio button is selected*/
    @FXML
    void onActionModifyPartOutsourced(ActionEvent event) {
        MachineIDorCompany.setText("Company Name");
    }

    /** This method saves a modified part object to the Inventory class.  The method retrieves various input values from the user and then checks
     * if the input values meet certain requirements and displays error messages if necessary.  If all input values are valid, it creates an InHouse or Outsourced
     * object based on the users selection and adds the Part object to the Inventory*/
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException {
        try {
            String name = ModifyPartNameTxt.getText();
            int inventory = Integer.parseInt(ModifyInventoryTxt.getText());
            double price = Double.parseDouble(ModifyPriceTxt.getText());
            int min = Integer.parseInt(ModifyMinTxt.getText());
            int max = Integer.parseInt(ModifyMaxTxt.getText());

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

            Part part = Inventory.getAllParts().get(currentIndex);
            if (ModifyInhouseRbtn.isSelected()) {
                machineID = Integer.parseInt(ModifyMachineTxt.getText());
                InHouse updatedPart = new InHouse(name, price, inventory, min, max, machineID);
                Inventory.updatePart(currentIndex,updatedPart);
            }

            if (ModifyOutsourcedRbtn.isSelected()) {
                companyName = ModifyMachineTxt.getText();
                Outsourced updatedPart = new Outsourced(name, price, inventory,min,max,companyName);
                Inventory.updatePart(currentIndex,updatedPart);
            }

            part.setName(name);
            part.setPrice(price);
            part.setStock(inventory);
            part.setMin(min);
            part.setMax(max);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }
}
