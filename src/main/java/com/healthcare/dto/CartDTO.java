package com.healthcare.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Session-based cart DTO stored in HttpSession.
 */
@Getter
@Setter
public class CartDTO {

    // medicineId -> CartItemDTO
    private Map<Long, CartItemDTO> items = new HashMap<>();

    public void addItem(Long medicineId, String medicineName, BigDecimal unitPrice, int quantity) {
        if (items.containsKey(medicineId)) {
            CartItemDTO item = items.get(medicineId);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(medicineId, new CartItemDTO(medicineId, medicineName, unitPrice, quantity));
        }
    }

    public void removeItem(Long medicineId) {
        items.remove(medicineId);
    }

    public void updateQuantity(Long medicineId, int quantity) {
        if (items.containsKey(medicineId)) {
            if (quantity <= 0) {
                items.remove(medicineId);
            } else {
                items.get(medicineId).setQuantity(quantity);
            }
        }
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
