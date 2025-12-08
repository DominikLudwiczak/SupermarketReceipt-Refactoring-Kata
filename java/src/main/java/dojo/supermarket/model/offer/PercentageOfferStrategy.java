package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Date;

public class PercentageOfferStrategy extends OfferStrategy {

    public PercentageOfferStrategy(Date startDate, Date endDate) {
        super(startDate, endDate);
    }

    public PercentageOfferStrategy() {
        super(null, null);
    }

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
