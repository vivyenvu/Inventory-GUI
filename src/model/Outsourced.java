package model;

public class Outsourced extends Part{
    private String companyName;

    public Outsourced(int partID, String partName, double partPrice, int partStock, int partMin, int partMax, boolean partMadeInHouse) {
        super(partID, partName, partPrice, partStock, partMin, partMax, partMadeInHouse);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }
}
