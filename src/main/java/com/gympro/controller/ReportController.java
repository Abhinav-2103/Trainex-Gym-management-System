package com.gympro.controller;

import com.gympro.service.PaymentService;
import com.gympro.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public String reports(@RequestParam(required = false) String startDate,
                          @RequestParam(required = false) String endDate,
                          Model model) {

        model.addAttribute("pageTitle", "Reports & Analytics");

        if (startDate != null && endDate != null && !startDate.isBlank() && !endDate.isBlank()) {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            model.addAttribute("filteredPayments", paymentService.getPaymentsByDateRange(start, end));
            model.addAttribute("filteredRevenue", paymentService.getRevenueByDateRange(start, end));
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        model.addAttribute("activeSubscriptions", subscriptionService.getActiveSubscriptions());
        model.addAttribute("expiredSubscriptions", subscriptionService.getExpiredSubscriptions());
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());

        return "reports";
    }
}
