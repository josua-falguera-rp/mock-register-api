package com.example.api.discount.dto;

import java.util.List;

public record DiscountResponse(
        double originalTotal,
        double discountAmount,
        double finalTotal,
        List<String> appliedDiscounts
) {}