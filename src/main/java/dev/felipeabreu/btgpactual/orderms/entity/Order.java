package dev.felipeabreu.btgpactual.orderms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tb_orders", indexes = {
        @Index(name = "customer_id_index", columnList = "clientId")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Order {

    @Id
    private Long orderId;

    private Long clientId;

    @ElementCollection
    @CollectionTable(
            name = "tb_order_items",
            joinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderItem> items;

    private BigDecimal price;
}
