package com.gympro.service;

import com.gympro.entity.Member;
import com.gympro.entity.Payment;
import com.gympro.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Member member;
    private Payment payment;

    @BeforeEach
    void setUp() {
        member = new Member("John Smith", "9876543210", "john@gympro.com", 30, "Male", "Miami", LocalDate.now());
        payment = new Payment(member, 2500.0, "UPI", LocalDate.now());
        payment.setId(1L);
    }

    @Test
    void testGetTotalRevenue() {
        when(paymentRepository.getTotalRevenue()).thenReturn(15000.0);

        Double total = paymentService.getTotalRevenue();

        assertEquals(15000.0, total);
        verify(paymentRepository, times(1)).getTotalRevenue();
    }

    @Test
    void testSavePayment() {
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment saved = paymentService.savePayment(payment);

        assertNotNull(saved);
        assertEquals(2500.0, saved.getAmount());
        assertEquals("UPI", saved.getPaymentMode());
    }

    @Test
    void testGetRevenueByDateRange() {
        LocalDate start = LocalDate.now().minusDays(30);
        LocalDate end = LocalDate.now();

        when(paymentRepository.getRevenueByDateRange(start, end)).thenReturn(5000.0);

        Double revenue = paymentService.getRevenueByDateRange(start, end);

        assertEquals(5000.0, revenue);
    }
}
