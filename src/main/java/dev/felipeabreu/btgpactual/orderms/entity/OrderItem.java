package dev.felipeabreu.btgpactual.orderms.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class OrderItem {

    private String product;
    private Integer quantity;
    private BigDecimal price;
}
