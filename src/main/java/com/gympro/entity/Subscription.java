package com.gympro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Member is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull(message = "Plan is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id", nullable = false)
    private MembershipPlan plan;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private String status; // "Active" or "Expired"

    // ── Constructors ──

    public Subscription() {}

    // ── Business Logic ──

    /**
     * Automatically calculates endDate and evaluates status
     * based on plan duration and current date.
     */
    public void calculateEndDateAndStatus() {
        if (this.startDate != null && this.plan != null && this.plan.getDurationInMonths() != null) {
            this.endDate = this.startDate.plusMonths(this.plan.getDurationInMonths());
        }
        evaluateStatus();
    }

    public void evaluateStatus() {
        if (this.endDate != null) {
            this.status = this.endDate.isBefore(LocalDate.now()) ? "Expired" : "Active";
        }
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public MembershipPlan getPlan() { return plan; }
    public void setPlan(MembershipPlan plan) { this.plan = plan; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
