package com.gympro.controller;

import com.gympro.entity.Member;
import com.gympro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class DashboardController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/")
    public String dashboard(Model model, Authentication authentication) {
        // KPI data
        model.addAttribute("totalMembers", memberService.getTotalMemberCount());
        model.addAttribute("activeSubscriptions", subscriptionService.countActive());
        model.addAttribute("expiredSubscriptions", subscriptionService.countExpired());
        model.addAttribute("monthlyRevenue", paymentService.getCurrentMonthRevenue());
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());

        // Chart data for the last 6 weeks
        double[] weeklyRevenue = paymentService.getWeeklyRevenueLast6Weeks();
        long[] weeklyAttendance = attendanceService.getWeeklyAttendanceLast6Weeks();
        model.addAttribute("weeklyRevenue", Arrays.toString(weeklyRevenue));
        model.addAttribute("weeklyAttendance", Arrays.toString(weeklyAttendance));

        // Recent members
        model.addAttribute("recentMembers", memberService.getRecentMembers());

        // Current user
        model.addAttribute("currentUser", authentication.getName());

        model.addAttribute("pageTitle", "Dashboard");
        return "dashboard";
    }
}
