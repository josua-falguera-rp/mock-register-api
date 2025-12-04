package com.example.api.discount.config;

import com.example.api.discount.rules.DiscountRuleRegistry;
import com.example.api.discount.rules.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up discount rules.
 */
@Configuration
public class DiscountConfiguration {

    /**
     * Set up your discount rules here.
     * This method runs automatically on application startup.
     * To add a new discount, add a registry.register() call with your rule.
     */
    @Bean
    CommandLineRunner discountSetup(DiscountRuleRegistry registry) {
        return args -> {

            // ============================================================
            // ADD YOUR DISCOUNT RULES BELOW
            // ============================================================

            registry.register(
                    new BOGORule("polar-pop-bogo")
                            .forProductContaining("POLAR POP")
                            .buyQuantity(1)
                            .getQuantity(1)
                            .discountPercent(100.0)  // 100% = free
            );

            registry.register(
                    new FixedDiscountRule("online-lotto-ticket-promo")
                            .forUpc("80")
                            .minQuantity(10)
                            .description("5$ off for every 10 online lotto ordered. ")
                            .fixedAmount(5.00)
            );

            registry.register(
                    new FixedDiscountRule("lotto-promo")
                            .forUpc("12345")
                            .fixedAmount(1.00)
                            .description("Lotto Promo: $1.00 off")
            );

            System.out.println("✓ Loaded " + registry.getAllRules().size() + " discount rules");
        };
    }
}