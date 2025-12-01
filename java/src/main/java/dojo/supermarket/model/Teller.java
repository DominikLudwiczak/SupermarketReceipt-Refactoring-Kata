package dojo.supermarket.model;

import dojo.supermarket.model.offer.OfferProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private final List<Offer> offers = new ArrayList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        offers.add(new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        Map<Product, Double> productQuantities = theCart.productQuantities();
        for (Map.Entry<Product, Double> pq: productQuantities.entrySet()) {
            Product p = pq.getKey();
            double quantity = pq.getValue();
            double unitPrice = catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }

        OfferProcessor.applyOffers(offers, productQuantities, catalog, receipt);

        return receipt;
    }
}
