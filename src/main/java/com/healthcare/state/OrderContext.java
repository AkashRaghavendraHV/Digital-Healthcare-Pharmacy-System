package com.healthcare.state;

import com.healthcare.model.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderContext {
    private OrderState currentState;

    public OrderContext(OrderState initialState) {
        this.currentState = initialState;
    }

    public void verify(Order order) {
        currentState.verify(this, order);
    }
    
    public void dispatch(Order order) {
        currentState.dispatch(this, order);
    }
    
    public void deliver(Order order) {
        currentState.deliver(this, order);
    }
    
    public void reject(Order order, String reason) {
        currentState.reject(this, order, reason);
    }
}
