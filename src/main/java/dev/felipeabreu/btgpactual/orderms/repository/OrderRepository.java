package dev.felipeabreu.btgpactual.orderms.repository;

import dev.felipeabreu.btgpactual.orderms.dto.OrderResponse;
import dev.felipeabreu.btgpactual.orderms.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByClientId(Long clientId, PageRequest pageRequest);
}
