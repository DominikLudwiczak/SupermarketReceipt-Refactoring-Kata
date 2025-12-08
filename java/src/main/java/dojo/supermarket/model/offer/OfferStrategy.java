package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.Map;

public interface OfferStrategy {
    void apply(Product product,
               double quantity,
               double unitPrice,
               double argument,
               Receipt receipt);
}
