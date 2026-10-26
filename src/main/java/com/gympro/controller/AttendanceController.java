package com.gympro.controller;

import com.gympro.entity.Attendance;
import com.gympro.entity.Member;
import com.gympro.service.AttendanceService;
import com.gympro.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String listAttendance(Model model) {
        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        model.addAttribute("pageTitle", "Attendance");
        return "attendance/list";
    }

    @GetMapping({"/add", "/mark"})
    public String addAttendanceForm(@RequestParam(value = "memberId", required = false) Long memberId, Model model) {
        Attendance attendance = new Attendance();
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setStatus("Present");
        if (memberId != null) {
            memberService.getMemberById(memberId).ifPresent(attendance::setMember);
        }
        if (attendance.getMember() == null) {
            attendance.setMember(new Member());
        }
        model.addAttribute("attendance", attendance);
        model.addAttribute("members", memberService.getAllMembers());
        model.addAttribute("pageTitle", "Mark Attendance");
        return "attendance/form";
    }

    @PostMapping("/save")
    public String saveAttendance(@Valid @ModelAttribute("attendance") Attendance attendance,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (attendance.getMember() == null || attendance.getMember().getId() == null) {
            result.rejectValue("member", "error.attendance", "Member is required");
        } else {
            memberService.getMemberById(attendance.getMember().getId())
                    .ifPresentOrElse(attendance::setMember,
                            () -> result.rejectValue("member", "error.attendance", "Selected member not found"));
        }

        if (result.hasErrors()) {
            model.addAttribute("members", memberService.getAllMembers());
            model.addAttribute("pageTitle", "Mark Attendance");
            return "attendance/form";
        }
        attendanceService.saveAttendance(attendance);
        redirectAttributes.addFlashAttribute("success", "Attendance recorded successfully!");
        return "redirect:/attendance";
    }

    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        attendanceService.deleteAttendance(id);
        redirectAttributes.addFlashAttribute("success", "Attendance record deleted!");
        return "redirect:/attendance";
    }
}
