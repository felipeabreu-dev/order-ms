package dev.felipeabreu.btgpactual.orderms.service;

import dev.felipeabreu.btgpactual.orderms.dto.OrderCreatedEvent;
import dev.felipeabreu.btgpactual.orderms.dto.OrderResponse;
import dev.felipeabreu.btgpactual.orderms.entity.Order;
import dev.felipeabreu.btgpactual.orderms.entity.OrderItem;
import dev.felipeabreu.btgpactual.orderms.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public void save(OrderCreatedEvent event) {
        var entity = new Order();
        entity.setOrderId(event.codigoPedido());
        entity.setClientId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setPrice(getPrice(event));

        repository.save(entity);
    }

    public Page<OrderResponse> findAllByCostumerId(Long clientId, PageRequest pageRequest) {
        var orders = repository.findAllByClientId(clientId, pageRequest);

        return orders.map(OrderResponse::fromEntity);
    }

    private BigDecimal getPrice(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(item -> new OrderItem(
                        item.produto(),
                        item.quantidade(),
                        item.preco()
                ))
                .toList();
    }
}
