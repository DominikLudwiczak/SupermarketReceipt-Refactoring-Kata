package dojo.supermarket.model.receipt;

import dojo.supermarket.model.discount.Discount;
import dojo.supermarket.model.product.Product;

import java.util.*;

public class Receipt {

    private final HashMap<Product, ReceiptItem> items = new HashMap<>();
    private final List<Discount> discounts = new ArrayList<>();

    public double getTotalPrice() {
        double total = 0.0;
        for (ReceiptItem item : items.values()) {
            total += item.getQuantity() * item.getPrice();
        }
        for (Discount discount : discounts) {
            total -= discount.getDiscountAmount();
        }
        return total;
    }

    public int getLoyaltyPoints() {
        return (int) getTotalPrice() / 3;
    }

    public void addProduct(Product p, double quantity, double price) {
        if(items.containsKey(p)) {
            ReceiptItem existingItem = items.get(p);
            double newQuantity = existingItem.getQuantity() + quantity;
            items.replace(p, existingItem, new ReceiptItem(p, newQuantity, price));
            return;
        }

        items.put(p, new ReceiptItem(p, quantity, price));
    }

    public Map<Product, ReceiptItem> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public void addDiscount(Discount discount) {
        discounts.add(discount);
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
