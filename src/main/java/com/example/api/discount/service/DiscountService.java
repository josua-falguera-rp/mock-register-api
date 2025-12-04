package com.example.api.discount.service;

import com.example.api.discount.dto.DiscountItemDTO;
import com.example.api.discount.dto.DiscountRequest;
import com.example.api.discount.dto.DiscountResponse;
import com.example.api.discount.rules.DiscountEngine;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountEngine discountEngine;

    public DiscountService(DiscountEngine discountEngine) {
        this.discountEngine = discountEngine;
    }

    public DiscountResponse calculateDiscount(DiscountRequest request) {
        // Calculate original total
        double originalTotal = request.items().stream()
                .mapToDouble(DiscountItemDTO::getLineTotal)
                .sum();

        // Apply all discount rules
        DiscountEngine.DiscountCalculation calculation =
                discountEngine.calculate(request.items());

        double finalTotal = Math.max(0, originalTotal - calculation.totalDiscount());

        return new DiscountResponse(
                originalTotal,
                calculation.totalDiscount(),
                finalTotal,
                calculation.appliedDiscounts()
        );
    }
}