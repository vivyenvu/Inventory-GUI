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

    public String getProdName() {
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

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public static String validProd(String name,String price, String stock, String min, String max) {
        double priced = 1.00;
        int stocki = 1;
        int mini = 0;
        int maxi =1;

        String invalid = "";
        try {
            priced = Double.parseDouble(price);
        }
        catch (NumberFormatException e) {
            invalid += "Price must be a double. ";
        }
        try {
            stocki = Integer.parseInt(stock);
        }
        catch (NumberFormatException e) {
            invalid += "Inventory must be an integer. ";
        }
        try {
            mini = Integer.parseInt(min);
        }
        catch (NumberFormatException e) {
            invalid += "Min must be an integer. ";
        }
        try {
            maxi = Integer.parseInt(max);
        }
        catch (NumberFormatException e) {
            invalid += "Max must be an integer. ";
        }

        if (name.isEmpty()) {
            invalid += "Name field is required. ";
        }
        if (priced <= 0) {
            invalid += "Price must be greater than $0 ";
        }
        if (stocki < mini || stocki > maxi) {
            invalid += "Stock must be between min and max values. ";
        }
        if (stocki < 1){
            invalid += "Stock must be greater than 0. ";
        }
        if (mini > maxi) {
            invalid += "Min must be less than max. ";
        }
        return invalid;
    }
}
