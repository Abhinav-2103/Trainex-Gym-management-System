package com.gympro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Trainer name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    @NotNull(message = "Salary is required")
    @Min(value = 1, message = "Salary must be greater than 0")
    private Double salary;

    // ── Constructors ──

    public Trainer() {}

    public Trainer(String name, String phone, String specialization, Double salary) {
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.salary = salary;
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
