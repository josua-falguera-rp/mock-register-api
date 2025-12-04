package com.example.api.discount.rules;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Registry that manages all active discount rules.
 * Rules can be added, removed, and retrieved from this registry.
 */
@Component
public class DiscountRuleRegistry {

    private final ConcurrentMap<String, DiscountRule> rules = new ConcurrentHashMap<>();

    /**
     * Registers a new discount rule.
     * If a rule with the same ID exists, it will be replaced.
     *
     * @param rule The rule to register
     */
    public void register(DiscountRule rule) {
        rules.put(rule.getRuleId(), rule);
    }

    /**
     * Returns all active rules sorted by priority.
     *
     * @return List of active rules
     */
    public List<DiscountRule> getActiveRules() {
        return rules.values().stream()
                .toList();
    }

    /**
     * Returns all registered rules.
     *
     * @return List of all rules
     */
    public List<DiscountRule> getAllRules() {
        return new ArrayList<>(rules.values());
    }
}