package com.example.api.discount.rules;

import com.example.api.discount.dto.DiscountItemDTO;

import java.util.List;

/**
 * Base interface for all discount rules.
 * Each rule can evaluate items and return discount information.
 */
public interface DiscountRule {

    /**
     * Evaluates the items and returns the discount result.
     *
     * @param items The cart items to evaluate
     * @return DiscountResult containing discount amount and description
     */
    DiscountResult apply(List<DiscountItemDTO> items);

    /**
     * Returns a unique identifier for this rule.
     */
    String getRuleId();
}