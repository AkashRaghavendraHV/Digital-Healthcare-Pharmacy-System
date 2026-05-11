package com.healthcare.controller;

import com.healthcare.model.Order;
import com.healthcare.repository.OrderRepository;
import com.healthcare.state.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pharmacist")
@RequiredArgsConstructor
public class PharmacistController {

    private final OrderRepository orderRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "pharmacist/dashboard";
    }

    @PostMapping("/orders/{id}/verify")
    public String verifyOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderContext context = getContextForOrder(order);
        context.verify(order);
        orderRepository.save(order);
        return "redirect:/pharmacist/dashboard";
    }

    @PostMapping("/orders/{id}/dispatch")
    public String dispatchOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderContext context = getContextForOrder(order);
        context.dispatch(order);
        orderRepository.save(order);
        return "redirect:/pharmacist/dashboard";
    }

    @PostMapping("/orders/{id}/deliver")
    public String deliverOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderContext context = getContextForOrder(order);
        context.deliver(order);
        orderRepository.save(order);
        return "redirect:/pharmacist/dashboard";
    }

    @PostMapping("/orders/{id}/reject")
    public String rejectOrder(@PathVariable Long id, @RequestParam String reason) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderContext context = getContextForOrder(order);
        context.reject(order, reason);
        orderRepository.save(order);
        return "redirect:/pharmacist/dashboard";
    }

    private OrderContext getContextForOrder(Order order) {
        OrderState initialState = switch (order.getStatus()) {
            case PLACED -> new PlacedState();
            case VERIFIED -> new VerifiedState();
            case DISPATCHED -> new DispatchedState();
            case DELIVERED -> new DeliveredState();
            case REJECTED -> new RejectedState();
        };
        return new OrderContext(initialState);
    }
}
