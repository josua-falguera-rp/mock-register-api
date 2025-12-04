package com.example.api.discount.rules;

/**
 * Represents the result of applying a discount rule.
 */
public record DiscountResult(
        double discountAmount,
        String description,
        boolean applied
) {
    public static DiscountResult noDiscount() {
        return new DiscountResult(0.0, "", false);
    }

    public static DiscountResult of(double amount, String description) {
        return new DiscountResult(amount, description, true);
    }
}