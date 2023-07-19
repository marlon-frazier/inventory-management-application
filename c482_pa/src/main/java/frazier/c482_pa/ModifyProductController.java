package frazier.c482_pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



/** This class handles all user interaction on the Modify Product Screen*/
public class ModifyProductController implements Initializable {

    @FXML
    private TableColumn<Part, Integer> ModifyAssociatedPartIdCol;

    @FXML
    private TableColumn<Part, String> ModifyAssociatedPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ModifyAssociatedProductInventoryCol;

    @FXML
    private TableColumn<Part, Double> ModifyAssociatedProductPriceCol;

    @FXML
    private TableView<Part> ModifyAssociatedProductTable;

    @FXML
    private TableColumn<Part, Integer> ModifyPartIdCol;

    @FXML
    private TableColumn<Part, String> ModifyPartNameCol;

    @FXML
    private TableColumn<Part, Integer> ModifyProductInventoryCol;

    @FXML
    private TextField ModifyProductInventoryTxt;

    @FXML
    private TextField ModifyProductMaxTxt;

    @FXML
    private TextField ModifyProductMinTxt;

    @FXML
    private TextField ModifyProductNameTxt;

    @FXML
    private TableColumn<Part, Double> ModifyProductPriceCol;

    @FXML
    private TextField ModifyProductPriceTxt;

    @FXML
    private TextField ModifyProductSearchBox;

    @FXML
    private TableView<Part> ModifyProductTable;

    @FXML
    private TextField ModifyProductId;

    Stage stage;
    Parent scene;
    private int currentIndex = 0;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This method is called when the 'Add' button is clicked on the Modify Product screen.  It adds a selected part to the list of associated
     * parts for the product being modified.
     * @param event An ActionEvent object (clicking the add button) representing the event triggered this method*/
    @FXML
    void onActionAddModifyProduct(ActionEvent event) {
        Part selectedPart = ModifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from the list");
            alert.showAndWait();
            return;
        } else if (!associatedParts.contains(selectedPart)) {
            associatedParts.add(selectedPart);
            ModifyAssociatedProductTable.setItems(associatedParts);
        }
    }

    /** This method sends the user back to the main menu when the 'Cancel' button is clicked*/
    @FXML
    void onActionBackToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method populates the Modify Product screen with data from a selected product object
     * @param selectedIndex the index of the selected product in the Product table
     * @param product the product object to populate data from*/
    public void matchProduct(int selectedIndex, Product product){
        currentIndex = selectedIndex;
        ModifyProductId.setText(String.valueOf(product.getId()));
        ModifyProductNameTxt.setText(String.valueOf(product.getName()));
        ModifyProductInventoryTxt.setText(String.valueOf(product.getStock()));
        ModifyProductPriceTxt.setText(String.valueOf(product.getPrice()));
        ModifyProductMaxTxt.setText(String.valueOf(product.getMax()));
        ModifyProductMinTxt.setText(String.valueOf(product.getMin()));

        for (Part part : product.getAllAssociatedParts()){
            associatedParts.add(part);
        }

    }

    /** This initialize method populates both table views in the Modify Product screen (top = parts, bottom = associated parts)*/
    @Override
    public void initialize(URL url, ResourceBundle rb){
        ModifyProductTable.setItems(Inventory.getAllParts());
        ModifyPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyAssociatedProductTable.setItems(associatedParts);
        ModifyAssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyAssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyAssociatedProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyAssociatedProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method removes the selected associated part from the modified product and updates the modified product table view.  If no part is selected, a warning message is displayed
     * @param event the event triggered by clicking the "Remove Associated Part" button*/
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = ModifyAssociatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select a part from the list");
            alert.showAndWait();
            return;
        } else if (associatedParts.contains(selectedPart)) {
            Product product = Inventory.getAllProducts().get(currentIndex);
            associatedParts.remove(selectedPart);
            ModifyAssociatedProductTable.setItems(associatedParts);
        }
    }

    /** This method handles the action of saving a modification to a product.  The modified values for the product are obtained from the user interface elements.
     * The method first validates the input values and then updates the corresponding product in the inventory.  If any input values are invalid, an alert with an appropiate
     * message is displayed to the user.
     * @param event the event that triggered the action (clicking the 'Save' button)
     * @throws IOException if there is an error loading the main menu FXML file*/
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException{
        try {
            String name = ModifyProductNameTxt.getText();
            int inventory = Integer.parseInt(ModifyProductInventoryTxt.getText());
            double price = Double.parseDouble(ModifyProductPriceTxt.getText());
            int min = Integer.parseInt(ModifyProductMinTxt.getText());
            int max = Integer.parseInt(ModifyProductMaxTxt.getText());

            if (inventory < min || inventory > max){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between minimum and maximum");
                alert.showAndWait();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
            }

            Product product = Inventory.getAllProducts().get(currentIndex);

            product.setName(name);
            product.setPrice(price);
            product.setStock(inventory);
            product.setMin(min);
            product.setMax(max);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect Value");
            alert.showAndWait();
            return;
        }
    }

    /** This method is an event handler for key release on Modify Product search box.  Searches and displays matching parts in the Modify Product table.
     * @param event The KeyEvent object that triggered the event (typing into the search box)*/
    @FXML
    void onKeyReleasedPartSearch(KeyEvent event) {
        String searchText = ModifyProductSearchBox.getText();
        if (searchText.isEmpty()) {
            ModifyProductTable.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> searchResult = Inventory.lookupPart(searchText);
        if (searchResult.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No matching parts");
            alert.setHeaderText("No parts found with the entered search criteria");
            alert.showAndWait();
        } else {
            ModifyProductTable.setItems(searchResult);
        }
    }
}
