package com.healthcare.state;

import com.healthcare.model.Order;

public interface OrderState {
    void verify(OrderContext context, Order order);
    void dispatch(OrderContext context, Order order);
    void deliver(OrderContext context, Order order);
    void reject(OrderContext context, Order order, String reason);
}
