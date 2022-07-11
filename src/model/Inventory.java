package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class stores parts and products.
 */
public class Inventory {
    /**
     * List of all parts in the inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * List of all products in the inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add a new part to the inventory's List of parts.
     * @param newPart new part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product to the Inventory's List of Products.
     * @param newProduct new product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part in the List of all parts based off id.
     * @param partID part identification number
     * @return part if found or null if not found
     */
    public static Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a product in the List of all products based off id.
     * @param productID product identification number
     * @return product if one is found or null if it is not found
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
     * Searches for a Part in the List of all parts based off name.
     * @param partName name of the part
     * @return list of parts found from allParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if ((part.getName()).contains(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Searches for a product in the List of all Products based off of name.
     * @param productName name of the product
     * @return List of products found from allProducts
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
     * Updates the part with a new part.
     * @param index index of part to update
     * @param selectedPart part used to replace old part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates selected product with new product.
     * @param index index of product to update
     * @param newProduct product used to replace old product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes the part that is selected.
     * @param selectedPart part to be deleted
     * @return true if deleted
     */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Deletes the product that is selected.
     * @param selectedProduct product to be deleted
     * @return true if deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * @return the List of every part in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the List of every product in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

