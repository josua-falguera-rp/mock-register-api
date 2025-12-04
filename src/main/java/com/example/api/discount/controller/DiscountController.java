package com.example.api.discount.controller;

import com.example.api.discount.dto.DiscountRequest;
import com.example.api.discount.dto.DiscountResponse;
import com.example.api.discount.service.DiscountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public ResponseEntity<DiscountResponse> calculateDiscount(
            @RequestBody DiscountRequest request) {
        DiscountResponse response = discountService.calculateDiscount(request);
        return ResponseEntity.ok(response);
    }
}