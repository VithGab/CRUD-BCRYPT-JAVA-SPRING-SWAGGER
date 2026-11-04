package com.vithgab.subscription_apis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vithgab.subscription_apis.model.Subscription;

import java.util.List;


public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByName(String name);

    List<Subscription> findByPrice(double price);

    List<Subscription> findByPaymentDay(Integer paymentDay);

    List<Subscription> findByClientId(Long clientId);
    boolean existsByClientId(Long clientId);

}
