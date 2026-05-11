package com.healthcare.strategy;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("upiPayment")
public class UpiPaymentStrategy implements PaymentStrategy {
    private static final Logger logger = LoggerFactory.getLogger(UpiPaymentStrategy.class);
    
    @Override
    public boolean pay(BigDecimal amount) {
        logger.info("STRATEGY: Processing UPI payment of ${}", amount);
        return true;
    }
}
