package com.healthcare.state;

import com.healthcare.model.Order;
import com.healthcare.enums.OrderStatus;

public class DispatchedState implements OrderState {
    @Override
    public void verify(OrderContext context, Order order) {
        throw new IllegalStateException("Order is already dispatched.");
    }

    @Override
    public void dispatch(OrderContext context, Order order) {
        throw new IllegalStateException("Order is already dispatched.");
    }

    @Override
    public void deliver(OrderContext context, Order order) {
        order.setStatus(OrderStatus.DELIVERED);
        context.setCurrentState(new DeliveredState());
    }

    @Override
    public void reject(OrderContext context, Order order, String reason) {
        throw new IllegalStateException("Cannot reject a dispatched order.");
    }
}
