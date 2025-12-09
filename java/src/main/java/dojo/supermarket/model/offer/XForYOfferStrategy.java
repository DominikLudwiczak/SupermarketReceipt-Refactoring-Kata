package dojo.supermarket.model.offer;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.discount.OneProductDiscount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.receipt.Receipt;

import java.util.Date;

public class XForYOfferStrategy extends OneProductOffer {
    private final int requiredQuantity;

    public XForYOfferStrategy(Date startDate, Date endDate, double argument, Product product,  int requiredQuantity) {
        super(startDate, endDate, argument, product);
        this.requiredQuantity = requiredQuantity;
    }

    public XForYOfferStrategy(double argument, Product product, int requiredQuantity) {
        super(argument, product);
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public void apply(Receipt receipt) {
        var receiptItem = receipt.getItems().getOrDefault(product, null);
        if (receiptItem == null) {
            return;
        }
        if (receiptItem.getQuantity() >= requiredQuantity) {
            int numberOfOffers = (int) (receiptItem.getQuantity() / requiredQuantity);
            double totalOfferPrice = numberOfOffers * argument * receiptItem.getPrice();
            double normalPrice = numberOfOffers * requiredQuantity * receiptItem.getPrice();
            double discountAmount = normalPrice - totalOfferPrice;

            if (discountAmount > 0) {
                receipt.addDiscount(new OneProductDiscount(product, numberOfOffers + " * " + requiredQuantity + " for " + argument, discountAmount));
            }
        }
    }
}
