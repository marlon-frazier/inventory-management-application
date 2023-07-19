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

/** This class handles all user interaction on the Add Product screen*/
public class AddProductController implements Initializable {
    @FXML
    private TableColumn<Part, String> AddPartNameCol;

    @FXML
    private TableColumn<Product, Integer> AddProductIdCol;

    @FXML
    private TableColumn<Product, Integer> AddProductInventoryCol;

    @FXML
    private TextField AddProductInventoryTxt;

    @FXML
    private TextField AddProductMaxTxt;

    @FXML
    private TextField AddProductMinTxt;

    @FXML
    private TextField AddProductNameTxt;

    @FXML
    private TableColumn<Product, Double> AddProductPriceCol;

    @FXML
    private TextField AddProductPriceTxt;

    @FXML
    private TextField AddProductSearchBox;

    @FXML
    private TableView<Part> AddProductTable;

    @FXML
    private TableColumn<?, ?> AssociatedInventoryCol;

    @FXML
    private TableColumn<?, ?> AssociatedPartIdCol;

    @FXML
    private TableColumn<?, ?> AssociatedPartNameCol;

    @FXML
    private TableColumn<?, ?> AssociatedPriceCol;

    @FXML
    private TableView<Part> AssociatedProductTable;

    Stage stage;
    Parent scene;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Adds a selected part to the associated parts list for the product being created. If no part is selected, an alert message will be displayed.
     * If the selected part is already in the associated parts list, nothing will happen.
     * @param event Action event that called the method (clicking the add button)*/
    @FXML
    void onActionAddProduct(ActionEvent event) {
        Part selectedPart = AddProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input error");
            alert.setContentText("Select part from list.");
            alert.showAndWait();
            return;
        } else if (!associatedParts.contains(selectedPart)) {
            associatedParts.add(selectedPart);
            AssociatedProductTable.setItems(associatedParts);
        }
    }

    /** This method takes the user back to the main menu when the cancel button is clicked
     * @param event The event is the click on the cancel button*/
    @FXML
    void onActionBackToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Removes a selected associated part from the Associated Parts table and the associatedParts list.  If no part is selected, a warning alert is displayed
     * @param event The event triggered by the 'Remove Part' button being clicked*/
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = AssociatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        } else if (associatedParts.contains(selectedPart)) {
            associatedParts.remove(selectedPart);
            AssociatedProductTable.setItems(associatedParts);
        }
    }

    /** This method is called when the 'save' button is clicked on the 'Add Product' screen; it retrieves the input values from the form fields, validates them,
     * and creates a new Product object, adds the associated parts, adds the Product to the Inventory, and navigates back to the main menu.
     * @param event The event that triggers this method call (clicking save button)
     * @throws IOException If an error occurs while loading the main menu FXML file*/
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException{
        try {
            String name = AddProductNameTxt.getText();
            int inventory = Integer.parseInt(AddProductInventoryTxt.getText());
            double price = Double.parseDouble(AddProductPriceTxt.getText());
            int min = Integer.parseInt(AddProductMinTxt.getText());
            int max = Integer.parseInt(AddProductMaxTxt.getText());

            if (inventory < min || inventory > max){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be between minimum and maximum");
                alert.showAndWait();
            } else if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
            }

            Product product = new Product(name,price,inventory,min,max);

            for (Part part: associatedParts) {
                if (!product.getAllAssociatedParts().contains(part)) {
                    product.addAssociatedParts(part);
                }
            }

            Inventory.getAllProducts().add(product);

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

    /** This initialize method populates both tables, the parts table and the associated parts table, with data*/
    @Override
    public void initialize(URL url, ResourceBundle rb){
        AddProductTable.setItems(Inventory.getAllParts());
        AddProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        AssociatedProductTable.setItems(associatedParts);
        AssociatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** Handles key release event in the search text field of the 'Add Product' form.  Filters the parts displayed in the 'Add Product' table based on the text entered in the search field.
     * @param event The KeyEvent triggered by a key release in the search text field.*/
    @FXML
    void onKeyReleasePartSearch(KeyEvent event) {
        String searchText = AddProductSearchBox.getText();
        if (searchText.isEmpty()) {
            AddProductTable.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> searchResult = Inventory.lookupPart(searchText);
        if (searchResult.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No matching parts");
            alert.setHeaderText("No parts found with the entered search criteria");
            alert.showAndWait();
        } else {
            AddProductTable.setItems(searchResult);
        }
    }

}
