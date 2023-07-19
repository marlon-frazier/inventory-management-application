package frazier.c482_pa;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class handles all user interaction on the main menu screen. */
public class MainMenuController implements Initializable {
    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    @FXML
    private TableColumn<Part, Integer> PartInventoryCol;

    @FXML
    private TableColumn<Part, String> PartNameCol;

    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    @FXML
    private TableView<Part> PartTableView;

    @FXML
    private TableColumn<Product, Integer> ProductIdCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, Double> ProductPriceCol;

    @FXML
    private TableView<Product> ProductTableView;
    @FXML
    private TextField PartSearchBox;
    @FXML
    private TextField ProductSearchBox;

    Stage stage;
    Parent scene;

    /** This method is called when the user clicks the 'Add Part' button in the main screen. It loads the Add Part FXML file and displays it in a new scene, which is shown on a new stage.
     *@param event The ActionEvent that triggered this method
     *@throws IOException If an error occurs while loading the AddPart.fxml file */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
       stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
    }

    /** This method is called when the user clicks the 'Add Product' button in the main screen. It loads the Add Product FXML file and displays it in a new scene, which is shown on a new stage
     *@param event The ActionEvent that triggered this method
     *@throws IOException If an error occurs while loading the AddProduct.fxml file */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException{
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method deletes the selected part from the inventory / tableview when the delete button is clicked in the UI.
     It first retrieves the selected part from the table view, and then prompts the user with a confirmation dialog.  If the user confirms the deletion, the selected part is removed from the inventory.
     @param event the event that triggered the method call */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("DELETING SELECTED PART");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(selectedPart);
        }
    }
    /** This method deletes the selected product from the inventory / tableview when the delete button is clicked in the UI.
     It first retrieves the selected product from the table view, and then prompts the user with a confirmation dialog.  If the user confirms the deletion, the selected product is removed from the inventory.
     LOGICAL / RUNTIME ERROR - For some reason, this method gave me a lot of trouble in regards to the deletion of a product that has associated parts connected to it.  It turns out that the answer
     was a lot more simple than I was making it out to be.  In the beginning I kept trying to check if selectedProduct.getAllAssociatedParts() was equal to null, but it kept crashing my program.  After
     some digging into some Java documentation, I realized that I had to call the size() method on getAllAssociatedParts and compare it to zero; which is essentially what I was trying to do in the first place, to
     get the method working properly.
     @param event the event that triggered the method call */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = ProductTableView.getSelectionModel().getSelectedItem();

        // Check if the selected product has associated parts
        if (selectedProduct.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Delete Product");
            alert.setContentText("The selected product has associated parts. Remove the associated parts before deleting the product.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("DELETING SELECTED PRODUCT");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deleteProduct(selectedProduct);
            }
        }
    }

    /** This method safely exits the application when the Exit button is clicked
     * @param event the event that triggered the method call */
    @FXML
    void onActionExitProgram(ActionEvent event) {
        System.exit(0);
    }

    /** This method handles the user clicking the "Modify Part" button by loading the ModifyPart FXML file view and initializing it with data for the currently selected part.  If no part is selected, displays an error message
     * @param event The ActionEvent representing the user's click on the 'Modify Part' button
     * @throws IOException if there is an error loading the ModifyPart.fxml view*/
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            Parent root = loader.load();

            ModifyPartController mpcontroller = loader.getController();
            mpcontroller.matchPart(PartTableView.getSelectionModel().getSelectedIndex(), PartTableView.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part to modify");
            alert.show();
        }
    }

    /** This method handles the user clicking the "Modify Product" button by loading the ModifyProduct FXML file view and initializing it with data for the currently selected product.  If no product is selected, displays an error message
     * @param event The ActionEvent representing the user's click on the 'Modify Product' button
     * @throws IOException if there is an error loading the ModifyProduct.fxml view*/
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            Parent root = loader.load();

            ModifyProductController mpcontroller = loader.getController();
            mpcontroller.matchProduct(ProductTableView.getSelectionModel().getSelectedIndex(), ProductTableView.getSelectionModel().getSelectedItem());

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product to modify");
            alert.show();
        }
    }

    /** This method initializes the main view by setting up the part and product tables, populating them with data from the Inventory class, and configuring the search boxes to filter the tables
     * @param url The location used to resolve relative paths for resources
     * @param rb The resources used to localize the view */
    @Override
    public void initialize(URL url, ResourceBundle rb){

        PartSearchBox.setOnKeyReleased(this::onKeyReleasedPartSearch);

        PartTableView.setItems(Inventory.getAllParts());
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTableView.setItems(Inventory.getAllProducts());
        ProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method filters the part table based on user input in the PartSearchBox. If the search box is empty, the table is populated with all parts.  If the
     * search box contains text, the table is filtered to show only the parts that match the search criteria
     * @param event The KeyEvent that triggered the method call */
    @FXML
    void onKeyReleasedPartSearch(KeyEvent event) {
        String searchText = PartSearchBox.getText();
        if (searchText.isEmpty()) {
            PartTableView.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> searchResult = Inventory.lookupPart(searchText);
        if (searchResult.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No matching parts");
            alert.setHeaderText("No parts found with the entered search criteria");
            alert.showAndWait();
        } else {
            PartTableView.setItems(searchResult);
        }
    }

    /** This method filters the part table based on user input in the ProductSearchBox. If the search box is empty, the table is populated with all products.  If the
     * search box contains text, the table is filtered to show only the products that match the search criteria
     * @param event The KeyEvent that triggered the method call*/
    @FXML
    void onKeyReleasedProductSearch(KeyEvent event) {
        String searchText = ProductSearchBox.getText();
        if (searchText.isEmpty()) {
            ProductTableView.setItems(Inventory.getAllProducts());
            return;
        }

        ObservableList<Product> searchResult = Inventory.lookupProduct(searchText);
        if (searchResult.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No matching products");
            alert.setHeaderText("No products found with the entered search criteria");
            alert.showAndWait();
        } else {
            ProductTableView.setItems(searchResult);
        }
    }


}
