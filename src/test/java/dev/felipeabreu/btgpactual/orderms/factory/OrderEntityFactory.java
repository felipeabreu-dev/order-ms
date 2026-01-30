package dev.felipeabreu.btgpactual.orderms.factory;

import dev.felipeabreu.btgpactual.orderms.entity.Order;
import dev.felipeabreu.btgpactual.orderms.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class OrderEntityFactory {


    public static Order build() {
        var itens = new OrderItem("notebook", 1, BigDecimal.valueOf(20.50));

        var entity = new Order();
        entity.setOrderId(1L);
        entity.setClientId(2L);
        entity.setPrice(BigDecimal.valueOf(20.50));
        entity.setItems(List.of(itens));

        return entity;
    }

    public static Page<Order> buildWithPage() {
        return new PageImpl<>(List.of(build()));
    }
}
