package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class stores Parts and Products
 */
public class Inventory {
    /**
     * List of all Parts in the Inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all Products in the Inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add a new Part to the Inventory's List of Parts
     * @param newPart, new part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product to the Inventory's List of Products
     * @param newProduct, new product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a Part in the List of all Parts based off id
     * @param partID, part identification number
     * @return part if found or null if not found
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getPartID() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a Product in the List of all Products based off id
     * @param productID, product identification number
     * @return produt if one is found or null if it is not found
     */
    public static Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getProdID() == productID) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches for a Part in the List of all Parts based off name
     * @param partName, name of the part
     * @return foundParts, list of parts found from allParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if ((part.getPartName()).contains(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Searches for a product in the List of all Products based off of name
     * @param productName, name of the product
     * @return foundProds, List of Products found from allProducts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProds = FXCollections.observableArrayList();
        for (Product prod : allProducts) {
            if ((prod.getProdName().contains(productName))) {
                foundProds.add(prod);
            }
        }
        return foundProds;
    }

    /**
     * Updates the part with a new part
     * @param index, index of part to update
     * @param selectedPart, part used to replace old part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product with new product
     * @param index, index of product to update
     * @param newProduct, product used to replace old product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the part that is selected
     * @param selectedPart, part to be deleted
     * @return true if deleted
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Deletes the product that is selected
     * @param selectedProduct, product to be deleted
     * @return true if deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * @return allParts, the List of every part in the Inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts, the list of every product in the Inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

