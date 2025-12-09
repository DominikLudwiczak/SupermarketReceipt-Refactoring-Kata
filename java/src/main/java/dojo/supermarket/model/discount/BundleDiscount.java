package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;

import java.util.HashMap;
import java.util.Set;

public class BundleDiscount extends Discount{
    private final Set<Product> bundleProducts;

    public BundleDiscount(Set<Product> bundleProducts, String description, double discountAmount) {
        super(description, discountAmount);
        this.bundleProducts = bundleProducts;
    }

    @Override
    public String getProductNames() {
        StringBuilder names = new StringBuilder();
        for (Product product : bundleProducts) {
            if (names.length() > 0) {
                names.append(", ");
            }
            names.append(product.getName());
        }
        return names.toString();
    }
}
