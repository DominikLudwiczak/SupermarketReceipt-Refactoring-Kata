package dojo.supermarket.model;

import dojo.supermarket.model.offer.*;
import dojo.supermarket.model.product.Product;
import dojo.supermarket.model.product.ProductUnit;
import dojo.supermarket.model.receipt.Receipt;
import dojo.supermarket.model.receipt.ReceiptItem;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    void tenPercentDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.KILO);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new PercentageOfferStrategy(10.0, toothbrush));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(apples);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5, receiptItem.getQuantity());

    }

    @Test
    void singleProductPurchase() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product rice = new Product("Rice", ProductUnit.EACH);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(rice, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.49, receipt.getTotalPrice(), 0.001);
        assertTrue(receipt.getDiscounts().isEmpty());
    }

    @Test
    void buyTwoGetOneFree() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("Toothbrush", ProductUnit.EACH);
        catalog.addProduct(toothbrush, 0.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new XForYOfferStrategy(2, toothbrush, 3));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.98, receipt.getTotalPrice(), 0.001);
    }

    @Test
    void fiveForSpecialPrice() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("Toothpaste", ProductUnit.EACH);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new XForAmountOfferStrategy(7.49, toothpaste, 5));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothpaste, 5);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7.49, receipt.getTotalPrice(), 0.001);
    }

    @Test
    void applesWeightBasedDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product apples = new Product("Apples", ProductUnit.KILO);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new PercentageOfferStrategy(10.0, apples));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apples, 2.0); // buying 2kg

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedWithoutDiscount = 2.0 * 1.99;
        double expectedWithDiscount = expectedWithoutDiscount * 0.9;

        assertEquals(expectedWithDiscount, receipt.getTotalPrice(), 0.001);
    }

    @Test
    void discountAppearsOnReceipt() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product rice = new Product("Rice", ProductUnit.EACH);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new PercentageOfferStrategy(10.0, rice));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(rice, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1, receipt.getDiscounts().size());
        assertTrue(receipt.getDiscounts().get(0).getDescription().contains("% off"));
    }

    @Test
    void bundleDiscountAppliedForCompleteBundle() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
        Product toothpaste = new Product("toothpaste", ProductUnit.EACH);

        catalog.addProduct(toothbrush, 0.99);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(new BundlePercentageOfferStrategy(10.0,
            new HashMap<>() {{
                put(toothbrush, 1.0);
                put(toothpaste, 1.0);
            }}
        ));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush, 1);
        cart.addItem(toothpaste, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(4.29, receipt.getTotalPrice(), 0.01);
    }


    @Test
    void testLoyaltyPoints() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product cereal = new Product("Cereal", ProductUnit.EACH);
        catalog.addProduct(cereal, 3.00);

        Teller teller = new Teller(catalog);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(cereal, 7);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7, receipt.getLoyaltyPoints());
    }

    @Test
    void testOfferValidityPeriod() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product milk = new Product("Milk", ProductUnit.EACH);
        catalog.addProduct(milk, 1.50);

        Teller teller = new Teller(catalog);
        var startDate = new Date(2023, 1, 1);
        var endDate = new Date(2023, 12, 31);
        teller.addSpecialOffer(new PercentageOfferStrategy(startDate, endDate, 20.0, milk));

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(milk, 2);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(3.00, receipt.getTotalPrice(), 0.001);
        assertTrue(receipt.getDiscounts().isEmpty());
    }
}
