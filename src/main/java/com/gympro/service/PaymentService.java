package com.gympro.service;

import com.gympro.entity.Payment;
import com.gympro.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    public Double getTotalRevenue() {
        return paymentRepository.getTotalRevenue();
    }

    public Double getMonthlyRevenue(int month, int year) {
        return paymentRepository.getMonthlyRevenue(month, year);
    }

    public Double getCurrentMonthRevenue() {
        LocalDate now = LocalDate.now();
        return paymentRepository.getMonthlyRevenue(now.getMonthValue(), now.getYear());
    }

    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByDateRange(startDate, endDate);
    }

    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.getRevenueByDateRange(startDate, endDate);
    }

    /**
     * Returns weekly revenue for the last 6 weeks for Chart.js data.
     */
    public double[] getWeeklyRevenueLast6Weeks() {
        double[] weeklyRevenue = new double[6];
        LocalDate today = LocalDate.now();
        for (int i = 5; i >= 0; i--) {
            LocalDate weekEnd = today.minusWeeks(5 - i);
            LocalDate weekStart = weekEnd.minusDays(6);
            weeklyRevenue[i] = paymentRepository.getRevenueByDateRange(weekStart, weekEnd);
        }
        return weeklyRevenue;
    }
}
