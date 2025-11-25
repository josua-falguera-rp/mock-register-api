package com.app.discount.service;

import com.app.discount.dto.DiscountItemDTO;
import com.app.discount.dto.DiscountRequest;
import com.app.discount.dto.DiscountResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountService {

    private static final String POLAR_POP_KEYWORD = "POLAR POP";
    private static final String LOTTO_PROMO_UPC = "12345";
    private static final double POLAR_POP_DISCOUNT_RATE = 0.10;
    private static final double LOTTO_PROMO_FIXED_DISCOUNT = 1.00;
    private static final int POLAR_POP_MIN_QUANTITY = 3;

    public DiscountResponse calculateDiscount(DiscountRequest request) {
        List<String> appliedDiscounts = new ArrayList<>();
        double originalTotal = 0.0;
        double totalPercentageDiscount = 0.0;
        boolean hasLottoPromoItem = false;

        for (DiscountItemDTO item : request.items()) {
            double lineTotal = item.getLineTotal();
            originalTotal += lineTotal;

            // Rule 1: Polar Pop bulk discount
            if (isPolarPopEligible(item)) {
                double discount = lineTotal * POLAR_POP_DISCOUNT_RATE;
                totalPercentageDiscount += discount;
                if (!appliedDiscounts.contains("Polar Pop Bulk: 10% off")) {
                    appliedDiscounts.add("Polar Pop Bulk: 10% off");
                }
            }

            // Check for Lotto Promo eligibility
            if (LOTTO_PROMO_UPC.equals(item.product().upc())) {
                hasLottoPromoItem = true;
            }
        }

        double discountAmount = totalPercentageDiscount;

        // Rule 2: Lotto Promo fixed discount (applied after percentage discounts)
        if (hasLottoPromoItem) {
            discountAmount += LOTTO_PROMO_FIXED_DISCOUNT;
            appliedDiscounts.add("Lotto Promo: $1.00 fixed discount");
        }

        double finalTotal = Math.max(0, originalTotal - discountAmount);

        return new DiscountResponse(
                originalTotal,
                discountAmount,
                finalTotal,
                appliedDiscounts
        );
    }

    private boolean isPolarPopEligible(DiscountItemDTO item) {
        String name = item.product().name();
        return name != null
                && name.toUpperCase().contains(POLAR_POP_KEYWORD)
                && item.quantity() >= POLAR_POP_MIN_QUANTITY;
    }
}