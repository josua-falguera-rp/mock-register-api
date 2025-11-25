package com.app.discount.dto;

import java.util.List;

public record DiscountResponse(
        double originalTotal,
        double discountAmount,
        double finalTotal,
        List<String> appliedDiscounts
) {}