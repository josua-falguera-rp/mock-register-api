package com.example.api.discount.rules;

import com.example.api.discount.dto.DiscountItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Engine that applies discount rules to items.
 */
@Component
public class DiscountEngine {

    private final DiscountRuleRegistry registry;

    public DiscountEngine(DiscountRuleRegistry registry) {
        this.registry = registry;
    }

    /**
     * Applies all active discount rules to the items.
     * Rules are applied in priority order.
     *
     * @param items The items to apply discounts to
     * @return DiscountCalculation containing total discount and descriptions
     */
    public DiscountCalculation calculate(List<DiscountItemDTO> items) {
        List<DiscountRule> rules = registry.getActiveRules();
        double totalDiscount = 0.0;
        List<String> appliedDiscounts = new ArrayList<>();

        for (DiscountRule rule : rules) {
            DiscountResult result = rule.apply(items);
            if (result.applied()) {
                totalDiscount += result.discountAmount();
                appliedDiscounts.add(result.description());
            }
        }

        return new DiscountCalculation(totalDiscount, appliedDiscounts);
    }

    /**
     * Result of discount calculation.
     */
    public record DiscountCalculation(
            double totalDiscount,
            List<String> appliedDiscounts
    ) {}
}