package com.gympro.service;

import com.gympro.entity.Member;
import com.gympro.entity.MembershipPlan;
import com.gympro.entity.Subscription;
import com.gympro.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private Member member;
    private MembershipPlan plan;
    private Subscription activeSubscription;
    private Subscription expiredSubscription;

    @BeforeEach
    void setUp() {
        member = new Member("Jane Doe", "9876543210", "jane@gympro.com", 26, "Female", "Boston", LocalDate.now());
        plan = new MembershipPlan("Gold", 6, 4500.0);

        activeSubscription = new Subscription();
        activeSubscription.setId(1L);
        activeSubscription.setMember(member);
        activeSubscription.setPlan(plan);
        activeSubscription.setStartDate(LocalDate.now().minusMonths(1));
        activeSubscription.calculateEndDateAndStatus();

        expiredSubscription = new Subscription();
        expiredSubscription.setId(2L);
        expiredSubscription.setMember(member);
        expiredSubscription.setPlan(plan);
        expiredSubscription.setStartDate(LocalDate.now().minusMonths(8));
        expiredSubscription.calculateEndDateAndStatus();
    }

    @Test
    void testActiveSubscriptionStatus() {
        assertEquals("Active", activeSubscription.getStatus());
        assertTrue(activeSubscription.getEndDate().isAfter(LocalDate.now()));
    }

    @Test
    void testExpiredSubscriptionStatus() {
        assertEquals("Expired", expiredSubscription.getStatus());
        assertTrue(expiredSubscription.getEndDate().isBefore(LocalDate.now()));
    }

    @Test
    void testSaveSubscriptionCalculatesEndDate() {
        Subscription newSub = new Subscription();
        newSub.setMember(member);
        newSub.setPlan(plan);
        newSub.setStartDate(LocalDate.now());

        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Subscription saved = subscriptionService.saveSubscription(newSub);

        assertNotNull(saved.getEndDate());
        assertEquals("Active", saved.getStatus());
        assertEquals(LocalDate.now().plusMonths(6), saved.getEndDate());
        verify(subscriptionRepository, times(1)).save(newSub);
    }

    @Test
    void testCountActiveAndExpired() {
        when(subscriptionRepository.findAll()).thenReturn(List.of(activeSubscription, expiredSubscription));

        long activeCount = subscriptionService.countActive();
        long expiredCount = subscriptionService.countExpired();

        assertEquals(1, activeCount);
        assertEquals(1, expiredCount);
    }
}
