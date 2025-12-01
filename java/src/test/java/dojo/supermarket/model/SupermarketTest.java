package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import java.util.Collections;

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
        teller.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5*1.99, receiptItem.getTotalPrice());
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
        teller.addSpecialOffer(SpecialOfferType.THREE_FOR_TWO, toothbrush, 0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(toothbrush, 3);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // Only pay for 2: 2 × 0.99 = 1.98
        assertEquals(1.98, receipt.getTotalPrice(), 0.001);
    }

    @Test
    void fiveForSpecialPrice() {
        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("Toothpaste", ProductUnit.EACH);
        catalog.addProduct(toothpaste, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FIVE_FOR_AMOUNT, toothpaste, 7.49);

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
        teller.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, apples, 10.0);

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
        teller.addSpecialOffer(SpecialOfferType.TEN_PERCENT_DISCOUNT, rice, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(rice, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1, receipt.getDiscounts().size());
        assertTrue(receipt.getDiscounts().get(0).getDescription().contains("% off"));
    }
}
