package dojo.supermarket.model.offer;

import dojo.supermarket.model.product.Product;

import java.util.Date;

public abstract class OneProductOffer extends Offer {
    Product product;

    public OneProductOffer(Date startDate, Date endDate, double argument, Product product) {
        super(startDate, endDate, argument);
        this.product = product;
    }

    public OneProductOffer(double argument, Product product) {
        super(argument);
        this.product = product;
    }
}
