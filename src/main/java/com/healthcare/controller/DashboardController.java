package com.healthcare.controller;

import com.healthcare.model.Patient;
import com.healthcare.model.User;
import com.healthcare.service.PatientDashboardFacade;
import com.healthcare.service.UserService;
import com.healthcare.service.AdminReportGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final PatientDashboardFacade patientDashboardFacade;

    @GetMapping("/patient/dashboard")
    public String patientDashboard(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findByEmail(principal.getName());
            if (user instanceof Patient) {
                Map<String, Object> dashboardData = patientDashboardFacade.getDashboardData((Patient) user);
                model.addAllAttributes(dashboardData);
                model.addAttribute("user", user);
            }
        }
        return "patient/dashboard";
    }

    @GetMapping("/doctor/dashboard")
    public String doctorDashboard() {
        return "doctor/dashboard";
    }



    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        Map<String, Object> report = AdminReportGenerator.getInstance().generateSystemReport();
        model.addAllAttributes(report);
        return "admin/dashboard";
    }
}
