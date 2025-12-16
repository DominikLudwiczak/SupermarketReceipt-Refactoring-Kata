## Changes Made to the Project

### Test Coverage
I added and extended unit tests to cover the existing checkout and pricing logic.  
These tests verify receipt totals, applied discounts, and different types of special offers.  
Having these tests in place allowed me to refactor the code safely and confirm that existing behavior was preserved.

---

### Refactoring and Code Structure
I refactored the project structure to better separate responsibilities and reduce code smells such as long methods and feature envy.

- Split the original model package into smaller, responsibility-focused packages:
  - `product`
  - `receipt`
  - `offer`
  - `discount`
- Moved pricing and discount calculation logic out of large conditional blocks into dedicated classes.
- Replaced enum-based conditional logic with explicit offer and discount classes.
- Introduced strategy classes for calculating different types of offers, which simplifies the checkout logic and makes it easier to add new offer types.

---

### Discounted Bundles
I implemented support for discounted product bundles.

- Added bundle-specific offer and discount classes.
- A bundle is defined as a fixed set of products.
- When all products in a bundle are purchased, a 10% discount is applied to the total price of those items.
- The discount is applied only for complete bundles; extra items outside a full bundle are not discounted.

---

### Coupon-Based Discounts
I added coupon support with the following rules:

- Coupons apply to specific products.
- Each coupon has a validity period (start and end date).
- Coupons are valid for a limited number of items (e.g. buy N items and get N at a discounted price).

Coupon discounts are calculated separately and added to the receipt only when all conditions are met.

---

### Loyalty Program

I implemented a loyalty program based on credit points.

- Money spent during purchases can be converted into credit points.

---

### Summary
As a result of these changes, the project now has improved test coverage, cleaner separation of responsibilities, reduced conditional complexity, and support for discounted bundles, coupon-based discounts, and a loyalty credit system.
