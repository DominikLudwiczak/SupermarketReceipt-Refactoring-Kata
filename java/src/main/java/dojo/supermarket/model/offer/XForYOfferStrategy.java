package dojo.supermarket.model.offer;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.Receipt;

public class XForYOfferStrategy implements OfferStrategy {
    private final int requiredQuantity;

    public XForYOfferStrategy(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public void apply(Product product, double quantity, double unitPrice, double argument, Receipt receipt) {
        if (quantity >= requiredQuantity) {
            int numberOfOffers = (int) (quantity / requiredQuantity);
            double totalOfferPrice = numberOfOffers * argument * unitPrice;
            double normalPrice = numberOfOffers * requiredQuantity * unitPrice;
            double discountAmount = normalPrice - totalOfferPrice;

            if (discountAmount > 0) {
                receipt.addDiscount(new Discount(product, numberOfOffers + " * " + requiredQuantity + " for " + argument, discountAmount));
            }
        }
    }
}
