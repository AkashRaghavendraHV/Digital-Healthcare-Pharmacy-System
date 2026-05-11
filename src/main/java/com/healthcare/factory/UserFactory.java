package com.healthcare.factory;

import com.healthcare.dto.RegistrationDTO;
import com.healthcare.model.*;
import com.healthcare.enums.Role;

public class UserFactory {

    public static User createUser(RegistrationDTO dto) {
        Role role = dto.getRole();
        User user;

        switch (role) {
            case PATIENT:
                Patient patient = new Patient();
                patient.setAddress(dto.getAddress());
                patient.setAge(dto.getAge());
                patient.setGender(dto.getGender());
                user = patient;
                break;
            case DOCTOR:
                Doctor doctor = new Doctor();
                doctor.setSpecialization(dto.getSpecialization());
                doctor.setExperience(dto.getExperience());
                user = doctor;
                break;
            case PHARMACIST:
                Pharmacist pharmacist = new Pharmacist();
                pharmacist.setPharmacyName(dto.getPharmacyName());
                pharmacist.setPharmacyAddress(dto.getPharmacyAddress());
                pharmacist.setLicenseNumber(dto.getLicenseNumber());
                user = pharmacist;
                break;
            case ADMIN:
                user = new Admin();
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setRole(role);
        
        return user;
    }
}
