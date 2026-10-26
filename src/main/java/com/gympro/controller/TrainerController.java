package com.gympro.controller;

import com.gympro.entity.Trainer;
import com.gympro.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @GetMapping
    public String listTrainers(Model model) {
        model.addAttribute("trainers", trainerService.getAllTrainers());
        model.addAttribute("pageTitle", "Trainers");
        return "trainers/list";
    }

    @GetMapping("/add")
    public String addTrainerForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        model.addAttribute("pageTitle", "Add Trainer");
        return "trainers/form";
    }

    @PostMapping("/save")
    public String saveTrainer(@Valid @ModelAttribute("trainer") Trainer trainer,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", trainer.getId() == null ? "Add Trainer" : "Edit Trainer");
            return "trainers/form";
        }
        trainerService.saveTrainer(trainer);
        redirectAttributes.addFlashAttribute("success", "Trainer saved successfully!");
        return "redirect:/trainers";
    }

    @GetMapping("/edit/{id}")
    public String editTrainerForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return trainerService.getTrainerById(id)
                .map(trainer -> {
                    model.addAttribute("trainer", trainer);
                    model.addAttribute("pageTitle", "Edit Trainer");
                    return "trainers/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Trainer not found!");
                    return "redirect:/trainers";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteTrainer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        trainerService.deleteTrainer(id);
        redirectAttributes.addFlashAttribute("success", "Trainer deleted successfully!");
        return "redirect:/trainers";
    }
}
