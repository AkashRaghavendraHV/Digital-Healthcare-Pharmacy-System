package com.healthcare.state;

import com.healthcare.model.Order;
import com.healthcare.enums.OrderStatus;

public class VerifiedState implements OrderState {
    @Override
    public void verify(OrderContext context, Order order) {
        throw new IllegalStateException("Order is already verified.");
    }

    @Override
    public void dispatch(OrderContext context, Order order) {
        order.setStatus(OrderStatus.DISPATCHED);
        context.setCurrentState(new DispatchedState());
    }

    @Override
    public void deliver(OrderContext context, Order order) {
        throw new IllegalStateException("Order must be dispatched before delivery.");
    }

    @Override
    public void reject(OrderContext context, Order order, String reason) {
        throw new IllegalStateException("Cannot reject an already verified order.");
    }
}
