package com.vithgab.subscription_apis.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vithgab.subscription_apis.exception.ResourceNotFoundException;
import com.vithgab.subscription_apis.model.Client;
import com.vithgab.subscription_apis.model.Subscription;
import com.vithgab.subscription_apis.repository.ClientRepository;
import com.vithgab.subscription_apis.repository.SubscriptionRepository;
import com.vithgab.subscription_apis.subscriptionsDTO.SubscriptionDTO;


@Service 
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, ClientRepository clientRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.clientRepository = clientRepository;
    }
    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }
    public Subscription getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }
    public List<Subscription> getSubscriptionByName(String name) {
        return subscriptionRepository.findByName(name);
    }
    public List<Subscription> getSubscriptionByPaymentDay(Integer paymentDay) {
        return subscriptionRepository.findByPaymentDay(paymentDay);
    }
    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
    public Subscription updateSubscription(Long id, Subscription subscriptionDetails) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found: " + id));

        existingSubscription.setName(subscriptionDetails.getName());
        existingSubscription.setCategory(subscriptionDetails.getCategory());
        existingSubscription.setPrice(subscriptionDetails.getPrice());
        existingSubscription.setPaymentDay(subscriptionDetails.getPaymentDay());

        return subscriptionRepository.save(existingSubscription);
    }
    @Transactional
    public Subscription createForClient(Long clientId, SubscriptionDTO dto) {
        Client client = clientRepository.findById(clientId)
        .orElseThrow(() -> new ResourceNotFoundException("Client", clientId));

    Subscription s = new Subscription();
    s.setName(dto.getName().trim());
    s.setCategory(dto.getCategory().trim());
    s.setPrice(dto.getPrice());
    s.setPaymentDay(dto.getPaymentDay());
    s.setClient(client);
    return subscriptionRepository.save(s);
    }
    @Transactional(readOnly = true)
    public List<Subscription> findByClient(Long clientId) {
    if (!clientRepository.existsById(clientId)) {
        throw new ResourceNotFoundException("Client", clientId);
    }
    return subscriptionRepository.findByClientId(clientId);
}
}
