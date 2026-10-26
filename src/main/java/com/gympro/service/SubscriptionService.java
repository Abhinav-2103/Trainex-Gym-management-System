package com.gympro.service;

import com.gympro.entity.Subscription;
import com.gympro.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subs = subscriptionRepository.findAll();
        // Re-evaluate status on every fetch to keep it current
        subs.forEach(Subscription::evaluateStatus);
        return subs;
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription saveSubscription(Subscription subscription) {
        subscription.calculateEndDateAndStatus();
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

    public long countActive() {
        // Re-count dynamically based on current date
        List<Subscription> all = subscriptionRepository.findAll();
        return all.stream()
                .peek(Subscription::evaluateStatus)
                .filter(s -> "Active".equals(s.getStatus()))
                .count();
    }

    public long countExpired() {
        List<Subscription> all = subscriptionRepository.findAll();
        return all.stream()
                .peek(Subscription::evaluateStatus)
                .filter(s -> "Expired".equals(s.getStatus()))
                .count();
    }

    public List<Subscription> getActiveSubscriptions() {
        List<Subscription> all = subscriptionRepository.findAll();
        all.forEach(Subscription::evaluateStatus);
        return all.stream().filter(s -> "Active".equals(s.getStatus())).toList();
    }

    public List<Subscription> getExpiredSubscriptions() {
        List<Subscription> all = subscriptionRepository.findAll();
        all.forEach(Subscription::evaluateStatus);
        return all.stream().filter(s -> "Expired".equals(s.getStatus())).toList();
    }
}
