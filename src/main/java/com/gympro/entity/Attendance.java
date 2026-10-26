package com.gympro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Member is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @NotNull(message = "Date is required")
    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @NotBlank(message = "Status is required")
    private String status; // "Present" or "Absent"

    // ── Constructors ──

    public Attendance() {}

    public Attendance(Member member, LocalDate attendanceDate, String status) {
        this.member = member;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    // ── Getters & Setters ──

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
