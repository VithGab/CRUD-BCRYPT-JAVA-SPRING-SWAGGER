package com.vithgab.subscription_apis.subscriptionsDTO;

import com.vithgab.subscription_apis.model.Subscription;

public record SubscriptionResponseDTO(Long id, String name, String category, double price, Integer paymentDay, Long clientId) {

    public static SubscriptionResponseDTO from(Subscription subscription) {
        return new SubscriptionResponseDTO(subscription.getId(), subscription.getName(),
                subscription.getCategory(), subscription.getPrice(),
                subscription.getPaymentDay(), subscription.getClient().getId()
        );
    }

}
