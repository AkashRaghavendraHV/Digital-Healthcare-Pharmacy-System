package com.healthcare.repository;

import com.healthcare.model.Patient;
import com.healthcare.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatientOrderByIssuedAtDesc(Patient patient);
    List<Prescription> findByPatientAndIsFulfilledFalseOrderByIssuedAtDesc(Patient patient);
    Optional<Prescription> findByAppointmentAppointmentId(Long appointmentId);
}
