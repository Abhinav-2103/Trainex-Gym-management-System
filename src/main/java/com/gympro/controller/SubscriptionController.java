package com.gympro.controller;

import com.gympro.entity.Member;
import com.gympro.entity.Subscription;
import com.gympro.service.MemberService;
import com.gympro.service.MembershipPlanService;
import com.gympro.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MembershipPlanService planService;

    @GetMapping
    public String listSubscriptions(Model model) {
        model.addAttribute("subscriptions", subscriptionService.getAllSubscriptions());
        model.addAttribute("pageTitle", "Subscriptions");
        return "subscriptions/list";
    }

    @GetMapping("/add")
    public String addSubscriptionForm(@RequestParam(value = "memberId", required = false) Long memberId, Model model) {
        Subscription subscription = new Subscription();
        subscription.setStartDate(LocalDate.now());
        subscription.setStatus("Active");
        if (memberId != null) {
            memberService.getMemberById(memberId).ifPresent(subscription::setMember);
        }
        if (subscription.getMember() == null) {
            subscription.setMember(new Member());
        }
        model.addAttribute("subscription", subscription);
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("plans", planService.getAllPlans());
        model.addAttribute("pageTitle", "Add Subscription");
        return "subscriptions/form";
    }

    @PostMapping("/save")
    public String saveSubscription(@Valid @ModelAttribute("subscription") Subscription subscription,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("plans", planService.getAllPlans());
            model.addAttribute("pageTitle", subscription.getId() == null ? "Add Subscription" : "Edit Subscription");
            return "subscriptions/form";
        }
        subscriptionService.saveSubscription(subscription);
        redirectAttributes.addFlashAttribute("success", "Subscription saved successfully!");
        return "redirect:/subscriptions";
    }

    @GetMapping("/edit/{id}")
    public String editSubscriptionForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return subscriptionService.getSubscriptionById(id)
                .map(subscription -> {
                    model.addAttribute("subscription", subscription);
                    model.addAttribute("members", memberService.getAllMembers());
                    model.addAttribute("plans", planService.getAllPlans());
                    model.addAttribute("pageTitle", "Edit Subscription");
                    return "subscriptions/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Subscription not found!");
                    return "redirect:/subscriptions";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteSubscription(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        subscriptionService.deleteSubscription(id);
        redirectAttributes.addFlashAttribute("success", "Subscription deleted successfully!");
        return "redirect:/subscriptions";
    }
}
