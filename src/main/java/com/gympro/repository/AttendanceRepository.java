package com.gympro.repository;

import com.gympro.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.status = 'Present' AND a.attendanceDate BETWEEN :startDate AND :endDate")
    long countPresentBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
