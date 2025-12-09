package dojo.supermarket.model.offer;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.discount.OneProductDiscount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.receipt.Receipt;

import java.util.Date;

public class PercentageOfferStrategy extends OneProductOffer {
    public PercentageOfferStrategy(Date startDate, Date endDate, double argument, Product product) {
        super(startDate, endDate, argument, product);
    }

    public PercentageOfferStrategy(double argument, Product product) {
        super(argument, product);
    }

    public void apply(Receipt receipt) {
        var receiptItem = receipt.getItems().getOrDefault(product, null);
        if (receiptItem == null) {
            return;
        }
        double discount = receiptItem.getPrice() * receiptItem.getQuantity() * (argument / 100);
        if (discount > 0.0) {
            receipt.addDiscount(new OneProductDiscount(product, argument + "% off", discount));
        }
    }
}
