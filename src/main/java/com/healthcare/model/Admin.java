package com.healthcare.model;

import com.healthcare.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends User {

    public Admin(String name, String email, String password, String phone) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setRole(Role.ADMIN);
    }
}
