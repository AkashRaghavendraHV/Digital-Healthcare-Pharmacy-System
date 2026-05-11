package com.healthcare.controller;

import com.healthcare.builder.AppointmentBuilder;
import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.model.User;
import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class AppointmentController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        List<User> doctors = userRepository.findAll().stream()
                .filter(u -> u instanceof Doctor)
                .collect(Collectors.toList());
        model.addAttribute("doctors", doctors);
        return "patient/doctors";
    }

    @GetMapping("/appointments")
    public String listAppointments(Model model, Principal principal) {
        Patient patient = (Patient) userService.findByEmail(principal.getName());
        model.addAttribute("appointments", appointmentRepository.findByPatientOrderByDateDescTimeDesc(patient));
        return "patient/appointments";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@RequestParam Long doctorId,
                                  @RequestParam String date,
                                  @RequestParam String time,
                                  Principal principal) {
                                  
        Patient patient = (Patient) userService.findByEmail(principal.getName());
        Doctor doctor = (Doctor) userRepository.findById(doctorId).orElseThrow();

        // Builder Pattern integration
        Appointment appointment = new AppointmentBuilder()
                .withPatient(patient)
                .withDoctor(doctor)
                .onDate(date)
                .atTime(time)
                .build();

        appointmentRepository.save(appointment);
        return "redirect:/patient/dashboard";
    }
}
