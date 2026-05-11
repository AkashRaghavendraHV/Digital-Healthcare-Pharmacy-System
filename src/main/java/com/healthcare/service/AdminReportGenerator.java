package com.healthcare.service;

import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.OrderRepository;
import com.healthcare.repository.UserRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Implementing Singleton Pattern as required by Minor Use Case 4
public class AdminReportGenerator {

    private static AdminReportGenerator instance;

    private AppointmentRepository appointmentRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    // Private constructor prevents direct instantiation
    private AdminReportGenerator() {
    }

    // Lazy initialization thread-safe
    public static synchronized AdminReportGenerator getInstance() {
        if (instance == null) {
            instance = new AdminReportGenerator();
        }
        return instance;
    }

    // Dependency injection method
    public void init(AppointmentRepository appointmentRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> generateSystemReport() {
        Map<String, Object> report = new HashMap<>();
        
        long totalUsers = userRepository != null ? userRepository.count() : 0;
        long totalOrders = orderRepository != null ? orderRepository.count() : 0;
        long totalAppointments = appointmentRepository != null ? appointmentRepository.count() : 0;
        
        BigDecimal revenue = orderRepository != null ? orderRepository.calculateTotalRevenue() : BigDecimal.ZERO;

        report.put("totalUsers", totalUsers);
        report.put("totalOrders", totalOrders);
        report.put("totalAppointments", totalAppointments);
        report.put("totalRevenue", revenue != null ? revenue : BigDecimal.ZERO);
        
        return report;
    }
}
