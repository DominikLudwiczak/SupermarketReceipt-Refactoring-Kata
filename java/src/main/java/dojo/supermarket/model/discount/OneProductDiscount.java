package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;

public class OneProductDiscount extends Discount {
    private final Product product;

    public OneProductDiscount(Product product, String description, double discountAmount) {
        super(description, discountAmount);
        this.product = product;
    }

    @Override
    public String getProductNames() {
        return product.getName();
    }
}
