package dojo.supermarket.model.offer;

import dojo.supermarket.model.product.Product;

import java.util.Date;
import java.util.HashMap;

public abstract class BundleOffer extends Offer {
    HashMap<Product, Double> bundleProducts;

    public BundleOffer(Date startDate, Date endDate, double argument, HashMap<Product, Double> bundleProducts) {
        super(startDate, endDate, argument);
        this.bundleProducts = bundleProducts;
    }

    public BundleOffer(double argument, HashMap<Product, Double> bundleProducts) {
        super(argument);
        this.bundleProducts = bundleProducts;
    }
}
