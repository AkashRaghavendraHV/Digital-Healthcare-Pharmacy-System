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
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Patient extends User {

    @NotBlank
    @Column(nullable = false)
    private String address;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer age;

    private String gender;

    public Patient(String name, String email, String password, String phone,
                   String address, Integer age, String gender) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setRole(Role.PATIENT);
        this.address = address;
        this.age = age;
        this.gender = gender;
    }
}
