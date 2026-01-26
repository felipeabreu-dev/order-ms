package dev.felipeabreu.btgpactual.orderms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private Long clientId;

    @ElementCollection
    private List<OrderItem> items;

    private BigDecimal price;
}
