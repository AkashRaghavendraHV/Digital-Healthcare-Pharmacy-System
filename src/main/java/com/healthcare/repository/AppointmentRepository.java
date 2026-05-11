package com.healthcare.repository;

import com.healthcare.enums.AppointmentStatus;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientOrderByDateDescTimeDesc(Patient patient);
    List<Appointment> findByDoctorOrderByDateDescTimeDesc(Doctor doctor);
    List<Appointment> findByDoctorAndStatus(Doctor doctor, AppointmentStatus status);
    List<Appointment> findByDoctorAndDateBetween(Doctor doctor, LocalDate from, LocalDate to);
    boolean existsByDoctorAndDateAndTime(Doctor doctor, LocalDate date, java.time.LocalTime time);

    @Query("SELECT a FROM Appointment a WHERE a.doctor = :doctor AND a.date = :date AND a.status NOT IN ('CANCELLED', 'COMPLETED')")
    List<Appointment> findActiveSlotsForDoctorOnDate(@Param("doctor") Doctor doctor, @Param("date") LocalDate date);
}
