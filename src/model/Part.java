package model;

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
    public static String validPart(String name,double price, int stock, int min, int max) {
        String invalid = "";
        if (name == null) {
            invalid += "Name field is required. ";
        }
        if (price <= 0) {
            invalid += "Price must be greater than $0 ";
        }
        if (stock < min || stock > max) {
            invalid += "Stock must be between min and max values. ";
        }
        if (min>max) {
            invalid += "Min must be less than max. ";
        }
        if (stock < 1){
            invalid += "Stock must be greater than 0. ";
        }
        return invalid;
    }
}
