package com.healthcare.dto;

import com.healthcare.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationDTO {
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "Role is required")
    private Role role;

    // Patient specific
    private String address;
    private Integer age;
    private String gender;

    // Doctor specific
    private String specialization;
    private Integer experience;

    // Pharmacist specific
    private String pharmacyName;
    private String pharmacyAddress;
    private String licenseNumber;
}
