package com.example.api.discount.rules.impl;

import com.example.api.discount.dto.DiscountItemDTO;
import com.example.api.discount.rules.DiscountResult;
import com.example.api.discount.rules.DiscountRule;

import java.util.List;

/**
 * BOGO (Buy One, Get One) discount rule.
 * Supports various configurations like BOGO free, BOGO 50% off, etc.
 */
public class BOGORule implements DiscountRule {

    private final String ruleId;
    private String targetUpc;
    private String targetKeyword;
    private double discountPercentage = 100.0; // 100% = free
    private int buyQuantity = 1;
    private int getQuantity = 1;

    public BOGORule(String ruleId) {
        this.ruleId = ruleId;
    }

    // Builder methods

    public BOGORule forUpc(String upc) {
        this.targetUpc = upc;
        return this;
    }

    public BOGORule forProductContaining(String keyword) {
        this.targetKeyword = keyword;
        return this;
    }

    public BOGORule discountPercent(double percent) {
        this.discountPercentage = percent;
        return this;
    }

    public BOGORule buyQuantity(int qty) {
        this.buyQuantity = qty;
        return this;
    }

    public BOGORule getQuantity(int qty) {
        this.getQuantity = qty;
        return this;
    }

    @Override
    public DiscountResult apply(List<DiscountItemDTO> items) {
        double totalDiscount = 0.0;

        for (DiscountItemDTO item : items) {
            if (matches(item)) {
                int qty = item.quantity();
                int setsOfDiscount = qty / (buyQuantity + getQuantity);
                int freeItems = setsOfDiscount * getQuantity;

                if (freeItems > 0) {
                    double itemPrice = item.product().price();
                    totalDiscount += (itemPrice * freeItems * discountPercentage / 100.0);
                }
            }
        }

        if (totalDiscount > 0) {
            String description = buildDescription();
            return DiscountResult.of(totalDiscount, description);
        }

        return DiscountResult.noDiscount();
    }

    private boolean matches(DiscountItemDTO item) {
        if (targetUpc != null && targetUpc.equals(item.product().upc())) {
            return true;
        }
        if (targetKeyword != null && item.product().name() != null) {
            return item.product().name().toUpperCase().contains(targetKeyword.toUpperCase());
        }
        return false;
    }

    private String buildDescription() {
        StringBuilder sb = new StringBuilder("Buy ");
        sb.append(buyQuantity).append(" Get ").append(getQuantity);

        if (discountPercentage == 100.0) {
            sb.append(" Free");
        } else {
            sb.append(" ").append((int)discountPercentage).append("% Off");
        }

        if (targetUpc != null) {
            sb.append(" (UPC: ").append(targetUpc).append(")");
        } else if (targetKeyword != null) {
            sb.append(" (").append(targetKeyword).append(")");
        }

        return sb.toString();
    }

    @Override
    public String getRuleId() {
        return ruleId;
    }
}