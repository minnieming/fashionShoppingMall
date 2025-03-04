package com.fashionmall.order.event;

import com.fashionmall.common.moduleApi.dto.OrderItemDto;
import com.fashionmall.common.moduleApi.util.ModuleApiUtil;
import com.fashionmall.order.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockRestoreEventListener {
    private final OrdersRepository ordersRepository;
    private final ModuleApiUtil moduleApiUtil;

    @Async
    @TransactionalEventListener(
            classes = StockRestoreEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void stockRestore(StockRestoreEvent event) {
        Long ordersId = event.getOrdersId();

        try {
            List<OrderItemDto> orderItemsByOrderId = ordersRepository.findOrderItemsByOrderId(ordersId);
            moduleApiUtil.restoreItemStockApi(orderItemsByOrderId);
        } catch (Exception e) {
            log.error("stockRestore error", e);
        }
    }
}