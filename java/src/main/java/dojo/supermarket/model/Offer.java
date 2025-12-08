package dojo.supermarket.model;

import dojo.supermarket.model.offer.OfferStrategy;

public class Offer {

    private final OfferStrategy offerStrategy;
    private final Product product;
    private final double argument;

    public Offer(OfferStrategy offerStrategy, Product product, double argument) {
        this.offerStrategy = offerStrategy;
        this.argument = argument;
        this.product = product;
    }

    public Product getProduct() { return product; }

    public void apply(double quantity, double unitPrice, Receipt receipt) {
        if(offerStrategy.isValid()) {
            offerStrategy.apply(product, quantity, unitPrice, argument, receipt);
        }
    }
}
