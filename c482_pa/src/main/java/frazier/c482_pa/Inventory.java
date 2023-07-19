package frazier.c482_pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Inventory class represents an inventory management system that stores and manages lists of Parts and Products*/
public class Inventory {
    /** The list of all Parts in the inventory*/
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /** The list of all Products in the inventory*/
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Adds a new Part to the inventory.*/
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /** Adds a new Product to the inventory*/
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Updates the Part at the given index with the given Part Object
     * @param index The index of the Part to be updated
     * @param selectedPart The updated Part object*/
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /** Updates the Product at the given index with the give Product object
     * @param index The index of the Product to be updated
     * @param selectedProduct The updated Product object*/
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /** Deletes the given Part from the inventory.
     * @param selectedPart The Part to be deleted from the inventory
     * @return true if the Part was deleted, false otherwise*/
    public static boolean deletePart (Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /** Deletes the given Product from the inventory.
     * @param selectedProduct The Part to be deleted from the inventory
     * @return true if the Product was deleted, false otherwise*/
    public static boolean deleteProduct (Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /** Gets a list of all Products in the inventory.
     * @return An ObservableList of all Products in the inventory*/
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Gets a list of all Parts in the inventory.
     * @return An ObservableList of all Parts in the inventory*/
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Searches for a Part in the inventory by name or ID.
     * @param searchTerm The search term to be used
     * @return An ObservableList of Parts that match the search term*/
    public static ObservableList<Part> lookupPart(String searchTerm) {
        ObservableList<Part> searchResults = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    Integer.toString(part.getId()).equals(searchTerm)) {
                searchResults.add(part);
            }
        }

        return searchResults;
    }

    /** Searches for a Product in the inventory by name or ID.
     * @param searchTerm The search term to be used
     * @return An ObservableList of Products that match the search term*/
    public static ObservableList<Product> lookupProduct(String searchTerm) {
        ObservableList<Product> searchResults = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    Integer.toString(product.getId()).equals(searchTerm)) {
                searchResults.add(product);
            }
        }

        return searchResults;
    }

    /** This method searches for a Part in the inventory by ID
     * @param partId the ID of the Part to search for
     * @return the Part with the specified ID, or null if not found*/
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }

        return null; // Part with the specified ID not found
    }

    /** This method searches for a Product in the inventory by ID
     * @param productId the ID of the Product to search for
     * @return the Product with the specified ID, or null if not found*/
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }

        return null; // Product with the specified ID not found
    }


}
