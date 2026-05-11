package com.healthcare.dto;

import com.healthcare.enums.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCheckoutDTO {

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    // Simulated payment fields
    private String cardNumber;
    private String upiId;
    private String netBankingBank;

    private Long prescriptionId;
}
