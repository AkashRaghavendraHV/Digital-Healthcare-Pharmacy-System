package com.healthcare.model;

import com.healthcare.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pharmacists")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Pharmacist extends User {

    @NotBlank
    @Column(nullable = false)
    private String pharmacyName;

    @NotBlank
    @Column(nullable = false)
    private String pharmacyAddress;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    public Pharmacist(String name, String email, String password, String phone,
                      String pharmacyName, String pharmacyAddress, String licenseNumber) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setRole(Role.PHARMACIST);
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.licenseNumber = licenseNumber;
    }
}
