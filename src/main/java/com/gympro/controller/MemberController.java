package com.gympro.controller;

import com.gympro.entity.Member;
import com.gympro.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("pageTitle", "Members");
        return "members/list";
    }

    @GetMapping("/add")
    public String addMemberForm(Model model) {
        model.addAttribute("member", new Member());
        model.addAttribute("pageTitle", "Add Member");
        return "members/form";
    }

    @PostMapping("/save")
    public String saveMember(@Valid @ModelAttribute("member") Member member,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", member.getId() == null ? "Add Member" : "Edit Member");
            return "members/form";
        }
        memberService.saveMember(member);
        redirectAttributes.addFlashAttribute("success", "Member saved successfully!");
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String editMemberForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return memberService.getMemberById(id)
                .map(member -> {
                    model.addAttribute("member", member);
                    model.addAttribute("pageTitle", "Edit Member");
                    return "members/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Member not found!");
                    return "redirect:/members";
                });
    }

    @GetMapping("/view/{id}")
    public String viewMember(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return memberService.getMemberById(id)
                .map(member -> {
                    model.addAttribute("member", member);
                    model.addAttribute("pageTitle", "Member Profile");
                    return "members/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Member not found!");
                    return "redirect:/members";
                });
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        memberService.deleteMember(id);
        redirectAttributes.addFlashAttribute("success", "Member deleted successfully!");
        return "redirect:/members";
    }

    @GetMapping("/search")
    public String searchMembers(@RequestParam("keyword") String keyword, Model model) {
        List<Member> members = memberService.searchMembers(keyword);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageTitle", "Search Results");
        return "members/list";
    }
}
