package dev.felipeabreu.btgpactual.orderms.factory;

import dev.felipeabreu.btgpactual.orderms.dto.OrderCreatedEvent;
import dev.felipeabreu.btgpactual.orderms.dto.OrderItemEvent;

import java.math.BigDecimal;
import java.util.List;

public class OrderCreatedEventFactory {

    public static OrderCreatedEvent build() {

        var items = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var event = new OrderCreatedEvent(1L, 2L, List.of(items));

        return event;
    }

    public static OrderCreatedEvent buildWithTwoItems() {
        var item = new OrderItemEvent("notebook", 1, BigDecimal.valueOf(20.50));
        var item2 = new OrderItemEvent("Tablet", 1, BigDecimal.valueOf(40.50));
        var event = new OrderCreatedEvent(1L, 2L, List.of(item, item2));

        return event;
    }
}
