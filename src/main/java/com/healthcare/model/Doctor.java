package com.healthcare.model;

import com.healthcare.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Doctor extends User {

    @NotBlank
    @Column(nullable = false)
    private String specialization;

    @Min(0)
    private Integer experience;

    @Column(columnDefinition = "TEXT")
    private String availableSlots; // JSON serialized weekly slot data

    public Doctor(String name, String email, String password, String phone,
                  String specialization, Integer experience) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setRole(Role.DOCTOR);
        this.specialization = specialization;
        this.experience = experience;
    }
}
