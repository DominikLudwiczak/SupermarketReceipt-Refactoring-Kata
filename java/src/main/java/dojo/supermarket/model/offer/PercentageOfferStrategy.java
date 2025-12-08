package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Map;

public class PercentageOfferStrategy implements OfferStrategy {

    @Override
    public void apply(Product product,
                      double quantity,
                      double unitPrice,
                      double argument,
                      Receipt receipt) {

        double discount = unitPrice * quantity * (argument / 100);
        if (discount > 0.0) {
            receipt.addDiscount(new Discount(product, argument + "% off", discount));
        }
    }
}
