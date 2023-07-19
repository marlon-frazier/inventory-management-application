package frazier.c482_pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Product class represents a product that can be sold by the inventory management system.  It contains the product ID, name, price
 * stock, minimum stock level, and maximum stock level.  The class also provides methods for managing associated parts with the product*/
public class Product {
    /** A list of associated parts for the product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** A unique ID for the product*/
    private static int lastId = 1000;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Product constructor*/
    public Product(String name, double price, int stock, int min, int max) {
        id = ++lastId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**@return returns id of the product*/
    public int getId() {
        return id;
    }

    /** @param id sets id of the product*/
    public void setId(int id) {
        this.id = id;
    }

    /**@return  returns name of product*/
    public String getName() {
        return name;
    }

    /** @param name sets name of the product*/
    public void setName(String name) {
        this.name = name;
    }

    /**@return returns price of the product*/
    public double getPrice() {
        return price;
    }

    /**@param price sets price of the product*/
    public void setPrice(double price) {
        this.price = price;
    }

    /** @return returns stock of product*/
    public int getStock() {
        return stock;
    }

    /** @param stock sets stock of the product*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** @return returns minimum stock level of the product*/
    public int getMin() {
        return min;
    }

    /** @param min sets minimum stock level of the product*/
    public void setMin(int min) {
        this.min = min;
    }

    /** @return  returns maximum stock level of the product*/
    public int getMax() {
        return max;
    }

    /** @param max sets maximum stock level of the product*/
    public void setMax(int max) {
        this.max = max;
    }

    /** Adds the associated part to the product*/
    public void addAssociatedParts(Part part){
        associatedParts.add(part);
    }

    /** Deletes associated part from the product*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /** Returns all the parts associated with a product; used to check if a product has associated parts before deletion*/
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }



}
