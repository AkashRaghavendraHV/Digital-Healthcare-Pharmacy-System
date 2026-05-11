package com.healthcare.state;

import com.healthcare.model.Order;

public class RejectedState implements OrderState {
    @Override
    public void verify(OrderContext context, Order order) {
        throw new IllegalStateException("Order is rejected.");
    }

    @Override
    public void dispatch(OrderContext context, Order order) {
        throw new IllegalStateException("Order is rejected.");
    }

    @Override
    public void deliver(OrderContext context, Order order) {
        throw new IllegalStateException("Order is rejected.");
    }

    @Override
    public void reject(OrderContext context, Order order, String reason) {
        throw new IllegalStateException("Order is already rejected.");
    }
}
