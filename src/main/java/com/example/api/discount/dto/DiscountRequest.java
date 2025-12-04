package com.example.api.discount.dto;

import java.util.List;

public record DiscountRequest(
        List<DiscountItemDTO> items
) {}