package com.fashionmall.order.event;

public class StockDeductEvent {

    private final Long ordersId;

    public StockDeductEvent(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getOrdersId() {
        return ordersId;
    }
}