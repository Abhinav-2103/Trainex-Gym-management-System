package com.gympro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "membership_plan")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Plan name is required")
    @Column(name = "plan_name", nullable = false)
    private String planName;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 month")
    @Max(value = 24, message = "Duration must be at most 24 months")
    @Column(name = "duration_in_months")
    private Integer durationInMonths;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    // ── Constructors ──

    public MembershipPlan() {}

    public MembershipPlan(String planName, Integer durationInMonths, Double price) {
        this.planName = planName;
        this.durationInMonths = durationInMonths;
        this.price = price;
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public Integer getDurationInMonths() { return durationInMonths; }
    public void setDurationInMonths(Integer durationInMonths) { this.durationInMonths = durationInMonths; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
