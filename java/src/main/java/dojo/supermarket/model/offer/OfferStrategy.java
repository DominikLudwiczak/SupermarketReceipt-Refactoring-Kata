package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Map;

public interface OfferStrategy {

    void apply(Offer offers,
               Map<Product, Double> productQuantities,
               SupermarketCatalog catalog,
               Receipt receipt);
}
