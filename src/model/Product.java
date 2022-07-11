package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Product type with List of associated parts.
 */
public class Product {
    private int prodID;
    private String prodName;
    private double prodPrice;
    private int prodStock;
    private int prodMin;
    private int prodMax;
    private ObservableList<Part> associatedParts;

    /**
     * Constructor for product object.
     * @param prodID product identification number
     * @param prodName name of product
     * @param prodPrice price of product
     * @param prodStock inventory level of product
     * @param prodMin minimum inventory level
     * @param prodMax maximum inventory level
     */
    public Product(int prodID, String prodName, double prodPrice, int prodStock, int prodMin, int prodMax) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodStock = prodStock;
        this.prodMin = prodMin;
        this.prodMax = prodMax;
        List<Part> partsList = new ArrayList<Part>();
        this.associatedParts = FXCollections.observableList(partsList);
    }

    //Getters

    /**
     * @return product identification number
     */
    public int getProdID() {
        return prodID;
    }

    /**
     * @return name of product
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * @return price of product
     */
    public double getProdPrice() {
        return prodPrice;
    }

    /**
     * @return inventory level of product
     */
    public int getProdStock() {
        return prodStock;
    }

    /**
     * @return minimum inventory level
     */
    public int getProdMin() {
        return prodMin;
    }

    /**
     * @return maximum inventory level
     */
    public int getProdMax() {
        return prodMax;
    }

    /**
     * @return List of parts associated with this product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    //Setters

    /**
     * @param prodID product identification number
     */
    public void setProdID (int prodID) {
        this.prodID = prodID;
    }

    /**
     * @param prodName name of product
     */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    /**
     * @param prodPrice price of product
     */
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    /**
     * @param prodStock inventory level of product
     */
    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    /**
     * @param prodMin minimum inventory level
     */
    public void setProdMin(int prodMin){
        this.prodMin = prodMin;
    }

    /**
     * @param prodMax maximum inventory level
     */
    public void setProdMax (int prodMax) {
        this.prodMax = prodMax;
    }

    /**
     * @param parts List of parts associated with product
     */
    public void setProdParts (ObservableList<Part> parts) {
        this.associatedParts = parts;
    }

    /**
     * Adds one part to the List of that product's associated parts.
     * @param part part to be added to associated parts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes specific part from product's list of associated parts.
     * @param selectedAssociatedPart part to be removed
     * @return true when deletion occurs
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        this.associatedParts.remove(selectedAssociatedPart);
        return true;
    }
}
