package dojo.supermarket.model.discount;

import dojo.supermarket.model.product.Product;

public abstract class Discount {

    private final String description;
    private final double discountAmount;

    public Discount(String description, double discountAmount) {
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() { return discountAmount;}
    public String getProductNames() { return "";}
}
