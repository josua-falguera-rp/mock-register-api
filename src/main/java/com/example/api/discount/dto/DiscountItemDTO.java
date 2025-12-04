package com.example.api.discount.dto;

public record DiscountItemDTO(
        DiscountProductDTO product,
        int quantity
) {
    public double getLineTotal() {
        return product.price() * quantity;
    }
}