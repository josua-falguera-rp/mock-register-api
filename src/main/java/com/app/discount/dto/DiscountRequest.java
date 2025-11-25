package com.app.discount.dto;

import java.util.List;

public record DiscountRequest(
        List<DiscountItemDTO> items
) {}