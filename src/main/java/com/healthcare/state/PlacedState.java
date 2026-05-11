package com.healthcare.state;

import com.healthcare.model.Order;
import com.healthcare.enums.OrderStatus;

public class PlacedState implements OrderState {
    @Override
    public void verify(OrderContext context, Order order) {
        order.setStatus(OrderStatus.VERIFIED);
        context.setCurrentState(new VerifiedState());
    }

    @Override
    public void dispatch(OrderContext context, Order order) {
        throw new IllegalStateException("Order must be verified before dispatch.");
    }

    @Override
    public void deliver(OrderContext context, Order order) {
        throw new IllegalStateException("Order must be dispatched before delivery.");
    }

    @Override
    public void reject(OrderContext context, Order order, String reason) {
        order.setStatus(OrderStatus.REJECTED);
        order.setRejectionReason(reason);
        context.setCurrentState(new RejectedState());
    }
}
