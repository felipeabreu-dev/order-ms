package dev.felipeabreu.btgpactual.orderms.repository;

import dev.felipeabreu.btgpactual.orderms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
