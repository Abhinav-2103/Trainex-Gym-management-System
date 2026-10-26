package com.gympro.controller;

import com.gympro.entity.MembershipPlan;
import com.gympro.service.MembershipPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/plans")
public class MembershipPlanController {

    @Autowired
    private MembershipPlanService planService;

    @GetMapping
    public String listPlans(Model model) {
        model.addAttribute("plans", planService.getAllPlans());
        model.addAttribute("pageTitle", "Membership Plans");
        return "plans/list";
    }

    @GetMapping("/add")
    public String addPlanForm(Model model) {
        model.addAttribute("plan", new MembershipPlan());
        model.addAttribute("pageTitle", "Add Plan");
        return "plans/form";
    }

    @PostMapping("/save")
    public String savePlan(@Valid @ModelAttribute("plan") MembershipPlan plan,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", plan.getId() == null ? "Add Plan" : "Edit Plan");
            return "plans/form";
        }
        planService.savePlan(plan);
        redirectAttributes.addFlashAttribute("success", "Plan saved successfully!");
        return "redirect:/plans";
    }

    @GetMapping("/edit/{id}")
    public String editPlanForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return planService.getPlanById(id)
                .map(plan -> {
                    model.addAttribute("plan", plan);
                    model.addAttribute("pageTitle", "Edit Plan");
                    return "plans/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Plan not found!");
                    return "redirect:/plans";
                });
    }

    @GetMapping("/delete/{id}")
    public String deletePlan(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        planService.deletePlan(id);
        redirectAttributes.addFlashAttribute("success", "Plan deleted successfully!");
        return "redirect:/plans";
    }
}
