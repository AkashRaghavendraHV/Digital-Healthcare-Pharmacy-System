package com.healthcare.controller;

import com.healthcare.enums.OrderStatus;
import com.healthcare.model.Order;
import com.healthcare.model.Patient;
import com.healthcare.model.Prescription;
import com.healthcare.repository.MedicineRepository;
import com.healthcare.repository.OrderRepository;
import com.healthcare.repository.PrescriptionRepository;
import com.healthcare.service.UserService;
import com.healthcare.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class OrderController {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ApplicationContext applicationContext;

    @GetMapping("/orders")
    public String listOrders(Model model, Principal principal) {
        Patient patient = (Patient) userService.findByEmail(principal.getName());
        model.addAttribute("orders", orderRepository.findByPatientOrderByPlacedAtDesc(patient));
        return "patient/orders";
    }

    @GetMapping("/prescriptions")
    public String listPrescriptions(Model model, Principal principal) {
        Patient patient = (Patient) userService.findByEmail(principal.getName());
        model.addAttribute("prescriptions", prescriptionRepository.findByPatientOrderByIssuedAtDesc(patient));
        return "patient/prescriptions";
    }

    @GetMapping("/prescriptions/{id}")
    public String viewPrescription(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionRepository.findById(id).orElseThrow();
        model.addAttribute("prescription", prescription);
        model.addAttribute("medicines", medicineRepository.findAll());
        return "patient/orderForm";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam Long prescriptionId,
                           @RequestParam BigDecimal amount,
                           @RequestParam String paymentMethod,
                           Principal principal) {
                           
        Patient patient = (Patient) userService.findByEmail(principal.getName());
        Prescription prescription = prescriptionRepository.findById(prescriptionId).orElseThrow();

        // Strategy Pattern Application
        PaymentStrategy strategy;
        if ("UPI".equalsIgnoreCase(paymentMethod)) {
            strategy = applicationContext.getBean("upiPayment", PaymentStrategy.class);
        } else {
            strategy = applicationContext.getBean("cardPayment", PaymentStrategy.class);
        }

        boolean paymentSuccess = strategy.pay(amount);

        if (paymentSuccess) {
            Order order = new Order();
            order.setPatient(patient);
            order.setPrescription(prescription);
            order.setTotalAmount(amount);
            order.setStatus(OrderStatus.PLACED);
            order.setPlacedAt(LocalDateTime.now());
            orderRepository.save(order);
            
            prescription.setFulfilled(true);
            prescriptionRepository.save(prescription);
        }

        return "redirect:/patient/dashboard";
    }
}
