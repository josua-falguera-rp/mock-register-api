package com.example.api.discount.rules.impl;

import com.example.api.discount.dto.DiscountItemDTO;
import com.example.api.discount.rules.DiscountResult;
import com.example.api.discount.rules.DiscountRule;

import java.util.List;

/**
 * Fixed discount rule that applies a flat discount amount
 * when specific products are in the cart.
 */
public class FixedDiscountRule implements DiscountRule {

    private final String ruleId;
    private String targetUpc;
    private double fixedAmount;
    private int minQuantity = 1;
    private String customDescription;

    public FixedDiscountRule(String ruleId) {
        this.ruleId = ruleId;
    }

    public FixedDiscountRule forUpc(String upc) {
        this.targetUpc = upc;
        return this;
    }

    public FixedDiscountRule fixedAmount(double amount) {
        this.fixedAmount = amount;
        return this;
    }

    public FixedDiscountRule minQuantity(int qty) {
        this.minQuantity = qty;
        return this;
    }

    public FixedDiscountRule description(String desc) {
        this.customDescription = desc;
        return this;
    }

    @Override
    public DiscountResult apply(List<DiscountItemDTO> items) {
        boolean eligible = false;

        for (DiscountItemDTO item : items) {
            if (matches(item) && item.quantity() >= minQuantity) {
                eligible = true;
                break;
            }
        }

        if (eligible) {
            String description = customDescription != null
                    ? customDescription
                    : buildDescription();
            return DiscountResult.of(fixedAmount, description);
        }

        return DiscountResult.noDiscount();
    }

    private boolean matches(DiscountItemDTO item) {
        return targetUpc != null && targetUpc.equals(item.product().upc());
    }

    private String buildDescription() {
        StringBuilder sb = new StringBuilder("$");
        sb.append(String.format("%.2f", fixedAmount));
        sb.append(" off");

        if (targetUpc != null) {
            sb.append(" (UPC: ").append(targetUpc).append(")");
        }

        return sb.toString();
    }

    @Override
    public String getRuleId() {
        return ruleId;
    }
}