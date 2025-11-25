package com.app.discount.controller;

import com.app.discount.dto.DiscountRequest;
import com.app.discount.dto.DiscountResponse;
import com.app.discount.service.DiscountService;
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