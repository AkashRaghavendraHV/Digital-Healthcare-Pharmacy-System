package com.healthcare.config;

import com.healthcare.repository.AppointmentRepository;
import com.healthcare.repository.OrderRepository;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.AdminReportGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SingletonConfig {

    @Bean
    public AdminReportGenerator adminReportGenerator(
            AppointmentRepository appointmentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {
        
        // Obtain the singleton instance
        AdminReportGenerator generator = AdminReportGenerator.getInstance();
        
        // Initialize it with Spring-managed repositories
        generator.init(appointmentRepository, orderRepository, userRepository);
        
        return generator;
    }
}
