package com.app.discount.dto;

public record DiscountProductDTO(
        String upc,
        String name,
        double price
) {}