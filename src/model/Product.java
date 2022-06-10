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
    //Getters
    public int getProdID() {
        return prodID;
    }

    public String getprodName() {
        return prodName;
    }

    public double getProdPrice() {
        return prodPrice;
    }

    public int getProdStock() {
        return prodStock;
    }

    public int getProdMin() {
        return prodMin;
    }

    public int getProdMax() {
        return prodMax;
    }
    //Setters

    public void setProdID (int prodID) {
        this.prodID = prodID;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }

    public void setProdMin(int prodMin){
        this.prodMin = prodMin;
    }

    public void setProdMax (int prodMax) {
        this.prodMax = prodMax;
    }


}
