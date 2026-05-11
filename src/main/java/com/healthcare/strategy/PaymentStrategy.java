package com.healthcare.strategy;
import java.math.BigDecimal;

public interface PaymentStrategy {
    boolean pay(BigDecimal amount);
}
