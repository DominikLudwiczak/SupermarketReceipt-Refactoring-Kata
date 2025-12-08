package dojo.supermarket.model;

import dojo.supermarket.model.offer.OfferStrategy;

public class Offer {

    OfferStrategy offerStrategy;
    private final Product product;
    double argument;

    public Offer(OfferStrategy offerStrategy, Product product, double argument) {
        this.offerStrategy = offerStrategy;
        this.argument = argument;
        this.product = product;
    }

    public Product getProduct() { return product; }

    public void apply(double quantity, double unitPrice, Receipt receipt) {
        offerStrategy.apply(product, quantity, unitPrice, argument, receipt);
    }
}
