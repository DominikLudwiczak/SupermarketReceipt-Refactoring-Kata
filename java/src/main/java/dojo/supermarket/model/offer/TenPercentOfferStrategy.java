package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Map;

public class TenPercentOfferStrategy implements OfferStrategy {

    @Override
    public void apply(Offer offer,
                      Map<Product, Double> productQuantities,
                      SupermarketCatalog catalog,
                      Receipt receipt) {
        Product product = offer.getProduct();
        if (productQuantities.containsKey(product)) {
            double quantity = productQuantities.get(product);
            double unitPrice = catalog.getUnitPrice(product);
            double discount = unitPrice * quantity * (offer.getArgument() / 100);
            if (discount > 0.0) {
                receipt.addDiscount(new Discount(product, "10% off", -discount));
            }
        }
    }
}
