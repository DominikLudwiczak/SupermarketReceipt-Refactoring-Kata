package dojo.supermarket.model.offer;

import dojo.supermarket.model.Product;
import dojo.supermarket.model.Receipt;

public class XForAmountOfferStrategy implements OfferStrategy {
    private final int requiredQuantity;

    public XForAmountOfferStrategy(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public void apply(Product product, double quantity, double unitPrice, double argument, Receipt receipt) {
        if (quantity >= requiredQuantity) {
            int numberOfOffers = (int) (quantity / requiredQuantity);
            double totalOfferPrice = numberOfOffers * argument;
            double normalPrice = numberOfOffers * requiredQuantity * unitPrice;
            double discountAmount = normalPrice - totalOfferPrice;

            if (discountAmount > 0) {
                receipt.addDiscount(new dojo.supermarket.model.Discount(product, numberOfOffers + " * " + requiredQuantity + " for " + argument, discountAmount));
            }
        }
    }
}
