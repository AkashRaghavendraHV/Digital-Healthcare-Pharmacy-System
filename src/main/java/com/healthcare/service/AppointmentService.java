package com.healthcare.service;

import com.healthcare.model.Appointment;
import com.healthcare.model.Patient;
import com.healthcare.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getPatientAppointments(Patient patient) {
        return appointmentRepository.findByPatientOrderByDateDescTimeDesc(patient);
    }
}
