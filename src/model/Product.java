package model;

import javafx.collections.ObservableList;

public class Product {
    private int prodID;
    private String prodName;
    private double prodPrice;
    private int prodStock;
    private int prodMin;
    private int prodMax;
    private ObservableList<Part> associatedParts;

    public Product(int prodID, String prodName, double prodPrice, int prodStock, int prodMin, int prodMax) {
        this.prodID = prodID;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodStock = prodStock;
        this.prodMin = prodMin;
        this.prodMax = prodMax;
    }
}
