package dojo.supermarket.model.offer;

import dojo.supermarket.model.*;

import java.util.List;
import java.util.Map;

public final class OfferProcessor {
    public static void applyOffers(List<Offer> offers,
                                   Map<Product, Double> productQuantities,
                                   SupermarketCatalog catalog,
                                   Receipt receipt) {

        for (Offer offer : offers) {
            OfferStrategy strategy = strategyFor(offer.getOfferType());
            if (strategy != null) {
                strategy.apply(offer, productQuantities, catalog, receipt);
            }
        }
    }

    private static OfferStrategy strategyFor(SpecialOfferType type) {
        switch (type) {
            case TEN_PERCENT_DISCOUNT:
                return new TenPercentOfferStrategy();
//            case THREE_FOR_TWO:
//                return new ThreeForTwoOfferStrategy();
//            case FIVE_FOR_AMOUNT:
//                return new FiveForAmountOfferStrategy();
//            case TWO_FOR_AMOUNT:
//                return new TwoForAmountOfferStrategy();
            default:
                return null;
        }
    }
}
