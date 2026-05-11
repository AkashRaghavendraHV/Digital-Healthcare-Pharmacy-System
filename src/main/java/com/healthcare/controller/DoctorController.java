package com.healthcare.controller;

import com.healthcare.enums.AppointmentStatus;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Prescription;
import com.healthcare.model.User;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.service.PrescriptionService;
import com.healthcare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentRepository appointmentRepository;
    private final UserService userService;
    private final PrescriptionService prescriptionService;

    @GetMapping("/appointments")
    public String listAppointments(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        if (user instanceof Doctor) {
            List<Appointment> appointments = appointmentRepository.findByDoctorOrderByDateDescTimeDesc((Doctor) user);
            model.addAttribute("appointments", appointments);
        }
        return "doctor/appointments";
    }

    @PostMapping("/appointments/{id}/status")
    public String updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            appointment.setStatus(AppointmentStatus.valueOf(status));
            appointmentRepository.save(appointment);
        });
        return "redirect:/doctor/appointments";
    }

    @GetMapping("/appointments/{id}/prescribe")
    public String prescribeForm(@PathVariable Long id, Model model) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            model.addAttribute("appointment", appointment);
            Prescription p = new Prescription();
            model.addAttribute("prescription", p);
        });
        return "doctor/prescribe";
    }

    @PostMapping("/appointments/{id}/prescribe")
    public String savePrescription(@PathVariable Long id, @ModelAttribute Prescription prescription) {
        appointmentRepository.findById(id).ifPresent(appointment -> {
            prescription.setAppointment(appointment);
            prescription.setPatient(appointment.getPatient());
            prescription.setDoctor(appointment.getDoctor());
            prescription.setIssuedAt(java.time.LocalDateTime.now());
            prescription.setValidUntil(java.time.LocalDate.now().plusDays(30));
            prescription.setFulfilled(false);
            
            // Generate using the Observer-implemented Service method.
            prescriptionService.generatePrescription(prescription);
            
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.save(appointment);
        });
        return "redirect:/doctor/appointments";
    }
}
