package model;

/**
 * Supplied class Part.java
 */

/**
 * @author Vivyen Vu
 */
public abstract class Part {
    private int partID;
    private String partName;
    private double partPrice;
    private int partStock;
    private int partMin;
    private int partMax;
    boolean madeInHouse;

    /**
     * Constructor for a Part
     * @param partID part identification number
     * @param partName name of part
     * @param partPrice price of part
     * @param partStock inventory level of part
     * @param partMin miniumum inventory level
     * @param partMax maximum inventory level
     * @param madeInHouse tells whether part was made in house or outsourced
     */
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

    /**
     * @return part identification number
     */
     public int getPartID() {
        return partID;
     }

    /**
     * @return part's name
     */
     public String getPartName() {
        return partName;
     }

    /**
     * @return part's price
     */
     public double getPartPrice() {
        return partPrice;
     }

    /**
     * @return part's inventory level
     */
     public int getPartStock() {
        return partStock;
     }

    /**
     * @return part's minimum inventory level
     */
     public int getPartMin() {
        return partMin;
     }

    /**
     * @return part's maximum inventory level
     */
     public int getPartMax() {
        return partMax;
     }

    /**
     * @return whether or not the part was made in house
     */
     public boolean isMadeInHouse() {
        return madeInHouse;
     }

     //Setters

    /**
     * @param partID the part's identification number
     */
    public void setPartID (int partID){
        this.partID = partID;
    }

    /**
     * @param partName the part's name
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * @param partPrice the part's price
     */
    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    /**
     * @param partStock the part's inventory level
     */
    public void setPartStock (int partStock) {
        this.partStock = partStock;
    }

    /**
     * @param partMin the part's minimum inventory level
     */
    public void setPartMin (int partMin) {
        this.partMin = partMin;
    }

    /**
     * @param partMax the part's maximum inventory level
     */
    public void setPartMax (int partMax) {
        this.partMax = partMax;
    }

    /**
     * @param madeInHouse whether or not the part was made in house
     */
    public void setMadeInHouse (boolean madeInHouse) {
        this.madeInHouse = madeInHouse;
    }

    /**
     * FUTURE ENHANCEMENT: Incorporate validation in setters to improve readability
     * and modularity of code.
     */

    /**
     * This method takes the data input in the text fields and validates them. Eg. name has to be a string and
     * cannot be empty, price must be a double, etc.
     * @param name name of part
     * @param price price of part
     * @param stock stock of part
     * @param min minimum inventory level
     * @param max maximum inventory level
     * @param machOrComp entered data that may either be the machineID or the companyName
     * @param madeInHouse whether or not the part was made in house
     * @return invalid which is a string including data validation errors
     */
    public static String validPart(String name,String price, String stock, String min, String max, String machOrComp, boolean madeInHouse) {
        double priced = 1.00;
        int stocki = 1;
        int mini = 0;
        int maxi =1;
        int machi;
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
        if (madeInHouse == true) {
            try {
                machi = Integer.parseInt(machOrComp);
            }
            catch (NumberFormatException e) {
                invalid += "Machine ID must be an integer. ";
            }
        }

        if (madeInHouse == false && machOrComp.isEmpty()) {
                invalid += "Machine ID must be an integer. ";
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
