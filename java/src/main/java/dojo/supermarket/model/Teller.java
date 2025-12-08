package dojo.supermarket.model;

import dojo.supermarket.model.offer.OfferStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private final List<Offer> offers = new ArrayList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(OfferStrategy offerStraegy, Product product, double argument) {
        offers.add(new Offer(offerStraegy, product, argument));
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

        for (Offer offer : offers) {
            Product product = offer.getProduct();
            if(productQuantities.containsKey(product)) {
                offer.apply(productQuantities.get(product), catalog.getUnitPrice(product), receipt);
            }
        }

        return receipt;
    }
}
