package com.healthcare.service;

import com.healthcare.model.Order;
import com.healthcare.model.Patient;
import com.healthcare.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getPatientOrders(Patient patient) {
        return orderRepository.findByPatientOrderByPlacedAtDesc(patient);
    }
}
