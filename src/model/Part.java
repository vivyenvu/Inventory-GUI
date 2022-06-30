package model;

import javafx.scene.control.Alert;

public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int partMin;
    private int partMax;
    boolean madeInHouse;

    public Part(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, boolean madeInHouse) {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partStock = partStock;
        this.partMin = partMin;
        this.partMax = partMax;
        this.madeInHouse = madeInHouse;
    }

    //Getters
     public int getPartID() {
        return partID;
     }

     public String getPartName() {
        return partName;
     }

     public double getPartPrice() {
        return partPrice;
     }

     public int getPartStock() {
        return partStock;
     }

     public int getPartMin() {
        return partMin;
     }

     public int getPartMax() {
        return partMax;
     }

     public boolean isMadeInHouse() {
        return madeInHouse;
     }

     //Setters
    public void setPartID (int partID){
        this.partID = partID;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    public void setPartStock (int partStock) {
        this.partStock = partStock;
    }

    public void setPartMin (int partMin) {
        this.partMin = partMin;
    }

    public void setPartMax (int partMax) {
        this.partMax = partMax;
    }
    
    public void setMadeInHouse (boolean madeInHouse) {
        this.madeInHouse = madeInHouse;
    }

    //Error message for onModPartSaveBtn
    public static String validPart(String name,String price, String stock, String min, String max) {
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
    }}
