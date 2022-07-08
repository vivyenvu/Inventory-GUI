package model;

/**
 * Class type Outsourced inherits from Part class
 */
public class Outsourced extends Part{
    /**
     * name of part's company
     */
    private String companyName;

    /**
     * Constructor for Outsourced Part object
     * @param partID part identification number
     * @param partName name of part
     * @param partPrice price of part
     * @param partStock inventory level of part
     * @param partMin minimum inventory level
     * @param partMax maximum inventory level
     * @param companyName name of company that made the part
     * @param madeInHouse should be false to say that this Outsourced object was not made in house
     */
    public Outsourced(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, String companyName, boolean madeInHouse) {
        super(partID, partName, partPrice, partStock, partMin, partMax, madeInHouse);
        this.companyName = companyName;
    }

    /**
     * Sets the company name
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company name
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }
}
