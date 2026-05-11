package com.healthcare.service;

import com.healthcare.model.Appointment;
import com.healthcare.model.Order;
import com.healthcare.model.Patient;
import com.healthcare.model.Prescription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientDashboardFacade {

    private final AppointmentService appointmentService;
    private final PrescriptionService prescriptionService;
    private final OrderService orderService;

    public Map<String, Object> getDashboardData(Patient patient) {
        Map<String, Object> data = new HashMap<>();

        List<Appointment> appointments = appointmentService.getPatientAppointments(patient);
        List<Prescription> unfulfilledPrescriptions = prescriptionService.getUnfulfilledPrescriptions(patient);
        List<Order> orders = orderService.getPatientOrders(patient);

        data.put("appointments", appointments);
        data.put("unfulfilledPrescriptions", unfulfilledPrescriptions);
        data.put("orders", orders);

        // Calculate summary stats
        data.put("upcomingAppointmentsCount", appointments.stream()
                .filter(a -> "REQUESTED".equals(a.getStatus().name()) || "CONFIRMED".equals(a.getStatus().name()))
                .count());
        data.put("unfulfilledPrescriptionsCount", unfulfilledPrescriptions.size());
        data.put("activeOrdersCount", orders.stream()
                .filter(o -> !"DELIVERED".equals(o.getStatus().name()) && !"REJECTED".equals(o.getStatus().name()))
                .count());

        return data;
    }
}
