package com.gympro.config;

import com.gympro.entity.*;
import com.gympro.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

/**
 * Seeds the H2 database with sample data on startup for demonstration.
 */
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AdminRepository adminRepo,
                               MemberRepository memberRepo,
                               MembershipPlanRepository planRepo,
                               SubscriptionRepository subRepo,
                               PaymentRepository paymentRepo,
                               TrainerRepository trainerRepo,
                               AttendanceRepository attendanceRepo,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            // ── Seed Admin ──
            if (adminRepo.count() == 0) {
                Admin admin = new Admin("admin", "admin@trainex.com", passwordEncoder.encode("admin123"));
                adminRepo.save(admin);
            }

            // ── Seed Membership Plans ──
            if (planRepo.count() == 0) {
                planRepo.save(new MembershipPlan("Basic Monthly", 1, 999.0));
                planRepo.save(new MembershipPlan("Standard Quarterly", 3, 2499.0));
                planRepo.save(new MembershipPlan("Premium Half-Yearly", 6, 4499.0));
                planRepo.save(new MembershipPlan("Elite Annual", 12, 7999.0));
            }

            // ── Seed Members ──
            if (memberRepo.count() == 0) {
                memberRepo.save(new Member("Aarav Sharma", "9810022341", "aarav@gmail.com", 25, "Male", "Delhi", LocalDate.now().minusDays(2)));
                memberRepo.save(new Member("Neha Verma", "9953410872", "neha@gmail.com", 28, "Female", "Mumbai", LocalDate.now().minusDays(3)));
                memberRepo.save(new Member("Rohan Gupta", "9873345120", "rohan@gmail.com", 30, "Male", "Bangalore", LocalDate.now().minusDays(5)));
                memberRepo.save(new Member("Simran Kaur", "9711266098", "simran@gmail.com", 22, "Female", "Chandigarh", LocalDate.now().minusDays(7)));
                memberRepo.save(new Member("Kabir Mehta", "9899931457", "kabir@gmail.com", 27, "Male", "Pune", LocalDate.now().minusDays(8)));
                memberRepo.save(new Member("Priya Patel", "9876543210", "priya@gmail.com", 24, "Female", "Ahmedabad", LocalDate.now().minusDays(15)));
                memberRepo.save(new Member("Arjun Singh", "9988776655", "arjun@gmail.com", 32, "Male", "Jaipur", LocalDate.now().minusDays(20)));
            }

            // ── Seed Trainers ──
            if (trainerRepo.count() == 0) {
                trainerRepo.save(new Trainer("Vikram Rathore", "9876501234", "Weight Training", 35000.0));
                trainerRepo.save(new Trainer("Ananya Das", "9876505678", "Yoga & Flexibility", 30000.0));
                trainerRepo.save(new Trainer("Rahul Kapoor", "9876509012", "CrossFit", 40000.0));
            }

            // ── Seed Subscriptions ──
            if (subRepo.count() == 0) {
                var members = memberRepo.findAll();
                var plans = planRepo.findAll();
                if (!members.isEmpty() && !plans.isEmpty()) {
                    for (int i = 0; i < Math.min(members.size(), plans.size()); i++) {
                        Subscription sub = new Subscription();
                        sub.setMember(members.get(i));
                        sub.setPlan(plans.get(i % plans.size()));
                        sub.setStartDate(LocalDate.now().minusMonths(i));
                        sub.calculateEndDateAndStatus();
                        subRepo.save(sub);
                    }
                    // Add an expired subscription for demo
                    Subscription expired = new Subscription();
                    expired.setMember(members.get(members.size() - 1));
                    expired.setPlan(plans.get(0));
                    expired.setStartDate(LocalDate.now().minusMonths(3));
                    expired.calculateEndDateAndStatus();
                    subRepo.save(expired);
                }
            }

            // ── Seed Payments ──
            if (paymentRepo.count() == 0) {
                var members = memberRepo.findAll();
                String[] modes = {"Cash", "UPI", "Card", "Online"};
                double[] amounts = {999, 2499, 4499, 7999, 1500, 3000, 2000};
                for (int i = 0; i < Math.min(members.size(), amounts.length); i++) {
                    paymentRepo.save(new Payment(members.get(i), amounts[i], modes[i % modes.length],
                            LocalDate.now().minusDays(i * 3)));
                }
            }

            // ── Seed Attendance ──
            if (attendanceRepo.count() == 0) {
                var members = memberRepo.findAll();
                for (int day = 0; day < 14; day++) {
                    for (int j = 0; j < Math.min(3, members.size()); j++) {
                        attendanceRepo.save(new Attendance(members.get(j),
                                LocalDate.now().minusDays(day),
                                day % 5 == 0 ? "Absent" : "Present"));
                    }
                }
            }
        };
    }
}
