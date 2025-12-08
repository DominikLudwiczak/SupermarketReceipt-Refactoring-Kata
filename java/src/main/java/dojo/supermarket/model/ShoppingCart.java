package dojo.supermarket.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Double> productQuantities = new HashMap<>();

    Map<Product, Double> productQuantities() { return Collections.unmodifiableMap(productQuantities); }

    void addItem(Product product) {
        addItemPackage(product, 1.0);
    }

    void addItem(Product product, double quantity) {
        addItemPackage(product, quantity);
    }

    private void addItemPackage(Product product, double quantity) {
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }
}
