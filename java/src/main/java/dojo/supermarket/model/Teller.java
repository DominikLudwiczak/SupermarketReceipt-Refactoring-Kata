package dojo.supermarket.model;

import dojo.supermarket.model.offer.Offer;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private final List<Offer> offers = new ArrayList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer offer) {
        offers.add(offer);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        for (Map.Entry<Product, Double> pq: theCart.productQuantities().entrySet()) {
            Product p = pq.getKey();
            double quantity = pq.getValue();
            double unitPrice = catalog.getUnitPrice(p);
            receipt.addProduct(p, quantity, unitPrice);
        }

        for (Offer offer : offers) {
            if(offer.isValid()) {
                offer.apply(receipt);
            }
        }

        return receipt;
    }
}
