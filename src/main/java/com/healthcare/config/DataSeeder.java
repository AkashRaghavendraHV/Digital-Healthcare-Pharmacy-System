package com.healthcare.config;

import com.healthcare.dto.RegistrationDTO;
import com.healthcare.enums.Role;
import com.healthcare.model.Medicine;
import com.healthcare.repository.MedicineRepository;
import com.healthcare.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(UserService userService, 
                                      MedicineRepository medicineRepository,
                                      com.healthcare.repository.AppointmentRepository appointmentRepository,
                                      com.healthcare.repository.OrderRepository orderRepository,
                                      com.healthcare.repository.UserRepository userRepository) {
        return args -> {
            // Initialize Singleton Pattern for Admin Reports
            com.healthcare.service.AdminReportGenerator.getInstance().init(appointmentRepository, orderRepository, userRepository);
            
            // Seed Admin if not exists
            if (userService.findByEmail("admin@healthcare.com") == null) {
                RegistrationDTO adminDto = new RegistrationDTO();
                adminDto.setName("System Admin");
                adminDto.setEmail("admin@healthcare.com");
                adminDto.setPassword("admin123");
                adminDto.setPhone("9999999999");
                adminDto.setRole(Role.ADMIN);
                userService.registerUser(adminDto);
            }

            // Seed Doctor if not exists
            if (userService.findByEmail("doctor@healthcare.com") == null) {
                RegistrationDTO doctorDto = new RegistrationDTO();
                doctorDto.setName("Dr. John Smith");
                doctorDto.setEmail("doctor@healthcare.com");
                doctorDto.setPassword("doctor123");
                doctorDto.setPhone("8888888888");
                doctorDto.setRole(Role.DOCTOR);
                doctorDto.setSpecialization("Cardiology");
                doctorDto.setExperience(15);
                userService.registerUser(doctorDto);
            }

            // Seed Patient if not exists
            if (userService.findByEmail("patient@healthcare.com") == null) {
                RegistrationDTO patientDto = new RegistrationDTO();
                patientDto.setName("Jane Doe");
                patientDto.setEmail("patient@healthcare.com");
                patientDto.setPassword("patient123");
                patientDto.setPhone("7777777777");
                patientDto.setRole(Role.PATIENT);
                patientDto.setAge(28);
                patientDto.setGender("Female");
                patientDto.setAddress("123 Main St, City");
                userService.registerUser(patientDto);
            }

            // Seed Pharmacist if not exists
            if (userService.findByEmail("pharma@healthcare.com") == null) {
                RegistrationDTO pharmaDto = new RegistrationDTO();
                pharmaDto.setName("City Pharmacy");
                pharmaDto.setEmail("pharma@healthcare.com");
                pharmaDto.setPassword("pharma123");
                pharmaDto.setPhone("6666666666");
                pharmaDto.setRole(Role.PHARMACIST);
                pharmaDto.setPharmacyName("City Health Pharmacy");
                pharmaDto.setPharmacyAddress("456 Market St, City");
                pharmaDto.setLicenseNumber("PH-12345");
                userService.registerUser(pharmaDto);
            }

            // Seed Medicines if empty
            if (medicineRepository.count() == 0) {
                Medicine m1 = new Medicine();
                m1.setName("Paracetamol 500mg");
                m1.setCategory("Painkiller");
                m1.setPrice(new BigDecimal("5.00"));
                m1.setStockQuantity(100);
                m1.setManufacturer("PharmaCorp");

                Medicine m2 = new Medicine();
                m2.setName("Amoxicillin 250mg");
                m2.setCategory("Antibiotic");
                m2.setPrice(new BigDecimal("15.50"));
                m2.setStockQuantity(50);
                m2.setManufacturer("BioMeds");

                Medicine m3 = new Medicine();
                m3.setName("Cetzine 10mg");
                m3.setCategory("Antihistamine");
                m3.setPrice(new BigDecimal("8.00"));
                m3.setStockQuantity(200);
                m3.setManufacturer("HealthLab");

                medicineRepository.saveAll(List.of(m1, m2, m3));
            }
        };
    }
}
