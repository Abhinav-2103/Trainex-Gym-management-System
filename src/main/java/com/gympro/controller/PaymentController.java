package com.gympro.controller;

import com.gympro.entity.Member;
import com.gympro.entity.Payment;
import com.gympro.service.MemberService;
import com.gympro.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        model.addAttribute("monthlyRevenue", paymentService.getCurrentMonthRevenue());
        model.addAttribute("pageTitle", "Payments");
        return "payments/list";
    }

    @GetMapping("/add")
    public String addPaymentForm(@RequestParam(value = "memberId", required = false) Long memberId, Model model) {
        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentMode("UPI");
        if (memberId != null) {
            memberService.getMemberById(memberId).ifPresent(payment::setMember);
        }
        if (payment.getMember() == null) {
            payment.setMember(new Member());
        }
        model.addAttribute("payment", payment);
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("pageTitle", "Add Payment");
        return "payments/form";
    }

    @PostMapping("/save")
    public String savePayment(@Valid @ModelAttribute("payment") Payment payment,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("pageTitle", payment.getId() == null ? "Add Payment" : "Edit Payment");
            return "payments/form";
        }
        paymentService.savePayment(payment);
        redirectAttributes.addFlashAttribute("success", "Payment recorded successfully!");
        return "redirect:/payments";
    }

    @GetMapping("/edit/{id}")
    public String editPaymentForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return paymentService.getPaymentById(id)
                .map(payment -> {
                    model.addAttribute("payment", payment);
                    model.addAttribute("members", memberService.getAllMembers());
                    model.addAttribute("pageTitle", "Edit Payment");
                    return "payments/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Payment not found!");
                    return "redirect:/payments";
                });
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        paymentService.deletePayment(id);
        redirectAttributes.addFlashAttribute("success", "Payment deleted successfully!");
        return "redirect:/payments";
    }
}
