package dev.felipeabreu.btgpactual.orderms.dto;

import dev.felipeabreu.btgpactual.orderms.factory.OrderEntityFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    OrderResponse orderResponse;

    @Nested
    class FromEntity {

        @Test
        void shouldMapCorrectly() {
            // Arrange
            var entity = OrderEntityFactory.build();

            // Act
            var response = orderResponse.fromEntity(entity);

            // Assert
            assertEquals(entity.getOrderId(), response.codigoPedido());
            assertEquals(entity.getClientId(), response.codigoCliente());
            assertEquals(entity.getPrice(), response.total());
        }
    }

}