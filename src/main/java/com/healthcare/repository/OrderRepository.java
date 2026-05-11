package com.healthcare.repository;

import com.healthcare.enums.OrderStatus;
import com.healthcare.model.Order;
import com.healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPatientOrderByPlacedAtDesc(Patient patient);
    List<Order> findByStatusOrderByPlacedAtAsc(OrderStatus status);
    List<Order> findByStatus(OrderStatus status);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.payment.status = 'SUCCESS'")
    BigDecimal calculateTotalRevenue();
}
