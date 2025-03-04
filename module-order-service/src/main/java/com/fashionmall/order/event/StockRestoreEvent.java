package com.fashionmall.order.event;

public class StockRestoreEvent {

    private final Long ordersId;

    public StockRestoreEvent(Long ordersId) {
        this.ordersId = ordersId;
    }

    public Long getOrdersId() {
        return ordersId;
    }
}