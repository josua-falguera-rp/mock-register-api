package com.example.api.discount.dto;

public record DiscountProductDTO(
        String upc,
        String name,
        double price
) {}