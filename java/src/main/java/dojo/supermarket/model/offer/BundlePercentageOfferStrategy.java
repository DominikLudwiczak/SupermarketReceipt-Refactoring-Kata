package dojo.supermarket.model.offer;

import dojo.supermarket.model.discount.BundleDiscount;
import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.receipt.Receipt;

import java.util.Date;
import java.util.HashMap;

public class BundlePercentageOfferStrategy extends BundleOffer {
    public BundlePercentageOfferStrategy(Date startDate, Date endDate, double argument, HashMap<Product, Double> bundleProducts) {
        super(startDate, endDate, argument, bundleProducts);
    }

    public BundlePercentageOfferStrategy(double argument, HashMap<Product, Double> bundleProducts) {
        super(argument, bundleProducts);
    }

    @Override
    public void apply(Receipt receipt) {
        double discount = 0.0;
        for (Product product : bundleProducts.keySet()) {
            var receiptItem = receipt.getItems().getOrDefault(product, null);
            if (receiptItem == null) {
                return;
            }
            double requiredQuantity = bundleProducts.get(product);
            var purchasedQuantity = receiptItem.getQuantity();
            if (purchasedQuantity < requiredQuantity) {
                return;
            }
            double unitPrice = receiptItem.getPrice();
            discount += unitPrice * requiredQuantity * (argument / 100);
        }

        receipt.addDiscount(new BundleDiscount(bundleProducts.keySet(), argument + "% off bundle", discount));
    }
}
