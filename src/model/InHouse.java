package model;

/**
 * Class type InHouse inherits from Part class
 */
public class InHouse extends Part{

    /**
     * Machine identification number
     */
    private int machineID;

    /**
     * Constructor for InHouse object
     * @param partID part identification number
     * @param partName name of part
     * @param partPrice price of part
     * @param partStock inventory level of part
     * @param partMin minimum inventory level
     * @param partMax maximum inventory level
     * @param machineID machine identification number
     * @param madeInHouse should be true to say that this InHouse object was made inhouse
     */
    public InHouse(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, int machineID, boolean madeInHouse) {
        super(partID, partName, partPrice, partStock, partMin, partMax, madeInHouse);
        this.machineID = machineID;
    }

    /**
     * Sets machineID
     * @param machineID machine identification number
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * Gets machine identification number
     * @return machine identification number
     */
    public int getMachineID() {
        return machineID;
    }
}
