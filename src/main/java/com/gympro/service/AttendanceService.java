package com.gympro.service;

import com.gympro.entity.Attendance;
import com.gympro.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    /**
     * Returns weekly attendance count (Present) for the last 6 weeks for Chart.js data.
     */
    public long[] getWeeklyAttendanceLast6Weeks() {
        long[] weeklyAttendance = new long[6];
        LocalDate today = LocalDate.now();
        for (int i = 5; i >= 0; i--) {
            LocalDate weekEnd = today.minusWeeks(5 - i);
            LocalDate weekStart = weekEnd.minusDays(6);
            weeklyAttendance[i] = attendanceRepository.countPresentBetween(weekStart, weekEnd);
        }
        return weeklyAttendance;
    }
}
