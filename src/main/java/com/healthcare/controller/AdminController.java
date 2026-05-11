package com.healthcare.controller;

import com.healthcare.model.Medicine;
import com.healthcare.repository.MedicineRepository;
import com.healthcare.repository.UserRepository;
import com.healthcare.service.AdminReportGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final MedicineRepository medicineRepository;

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/medicines")
    public String manageMedicines(Model model) {
        model.addAttribute("medicines", medicineRepository.findAll());
        return "admin/medicines";
    }

    @PostMapping("/medicines/add")
    public String addMedicine(@ModelAttribute Medicine medicine) {
        medicineRepository.save(medicine);
        return "redirect:/admin/medicines";
    }

    @PostMapping("/medicines/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineRepository.deleteById(id);
        return "redirect:/admin/medicines";
    }
}
