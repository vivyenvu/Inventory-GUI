package model;

public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int partMin;
    private int partMax;
    boolean partMadeInHouse;

    public Part(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, boolean partMadeInHouse) {
        this.partID = partID;
        this.partName = partName;
        this.partPrice = partPrice;
        this.partStock = partStock;
        this.partMin = partMin;
        this.partMax = partMax;
        this.partMadeInHouse = partMadeInHouse;
    }

    //getters
     public int getID() {
        return partID;
     }

     public String getName() {
        return partName;
     }

     public double getPrice() {
        return partPrice;
     }

     public int getStock() {
        return partStock;
     }

     public int getMin() {
        return partMin;
     }

     public int getMax() {
        return partMax;
     }

     public boolean isPartMadeInHouse() {
        return partMadeInHouse;
     }
}
