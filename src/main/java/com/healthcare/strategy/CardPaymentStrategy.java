package com.healthcare.strategy;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cardPayment")
public class CardPaymentStrategy implements PaymentStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CardPaymentStrategy.class);
    
    @Override
    public boolean pay(BigDecimal amount) {
        logger.info("STRATEGY: Processing Credit/Debit Card payment of ${}", amount);
        return true;
    }
}
