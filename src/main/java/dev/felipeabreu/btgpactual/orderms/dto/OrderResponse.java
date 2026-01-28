package dev.felipeabreu.btgpactual.orderms.dto;

import dev.felipeabreu.btgpactual.orderms.entity.Order;

import java.math.BigDecimal;

public record OrderResponse(
        Long codigoPedido,
        Long codigoCliente,
        BigDecimal total
) {

    public static OrderResponse fromEntity(Order order) {
        return new OrderResponse(order.getOrderId(), order.getClientId(), order.getPrice());
    }
}
