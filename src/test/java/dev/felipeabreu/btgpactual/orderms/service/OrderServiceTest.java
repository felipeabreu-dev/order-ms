package dev.felipeabreu.btgpactual.orderms.service;

import dev.felipeabreu.btgpactual.orderms.entity.Order;
import dev.felipeabreu.btgpactual.orderms.factory.OrderCreatedEventFactory;
import dev.felipeabreu.btgpactual.orderms.factory.OrderEntityFactory;
import dev.felipeabreu.btgpactual.orderms.repository.OrderRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @Mock
    OrderRepository orderRepository;
    
    @InjectMocks
    OrderService orderService;

    @Captor
    ArgumentCaptor<Order> orderArgumentCaptor;

    @Nested
    class Save {

        @Test
        void shouldCallRepositorySave() {
            // Arrange
            var event = OrderCreatedEventFactory.build();

            // Act
            orderService.save(event);

            // Assert
            verify(orderRepository, times(1)).save(any());
        }

        @Test
        void shouldMapEventToEntityWithSuccess() {
            // Arrange
            var event = OrderCreatedEventFactory.build();

            // Act
            orderService.save(event);

            // Assert
            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var entity = orderArgumentCaptor.getValue();

            assertEquals(event.codigoPedido(), entity.getOrderId());
            assertEquals(event.codigoCliente(), entity.getClientId());
            assertNotNull(entity.getPrice());
            assertEquals(event.itens().getFirst().produto(), entity.getItems().getFirst().getProduct());
            assertEquals(event.itens().getFirst().quantidade(), entity.getItems().getFirst().getQuantity());
            assertEquals(event.itens().getFirst().preco(), entity.getItems().getFirst().getPrice());
        }

        @Test
        void shouldCalculateOrderTotalWithSuccess() {
            // Arrange
            var event = OrderCreatedEventFactory.buildWithTwoItems();
            var totalItem1 = event.itens().getFirst().preco().multiply(BigDecimal.valueOf(event.itens().getFirst().quantidade()));
            var totalItem2 = event.itens().getLast().preco().multiply(BigDecimal.valueOf(event.itens().getLast().quantidade()));
            var orderTotal = totalItem1.add(totalItem2);


            // Act
            orderService.save(event);

            // Assert
            verify(orderRepository, times(1)).save(orderArgumentCaptor.capture());

            var entity = orderArgumentCaptor.getValue();

            assertNotNull(entity.getPrice());
            assertEquals(orderTotal, entity.getPrice());
        }
    }

    @Nested
    class FindAllByClientId {

        @Test
        void shouldCallRepositoryCorrectly() {
            // Arrange
            var clientId = 1L;
            var pageRequest = PageRequest.of(0, 10);
            doReturn(OrderEntityFactory.buildWithPage())
                    .when(orderRepository).findAllByClientId(eq(clientId), eq(pageRequest));

            // Act
            orderService.findAllByCostumerId(clientId, pageRequest);

            // Assert
            verify(orderRepository, times(1)).findAllByClientId(eq(clientId), eq(pageRequest));
        }

        @Test
        void shouldMapResponse() {
            // Arrange
            var clientId = 1L;
            var pageRequest = PageRequest.of(0, 10);
            var page = OrderEntityFactory.buildWithPage();
            doReturn(page)
                    .when(orderRepository).findAllByClientId(anyLong(), any());

            // Act
            var response = orderService.findAllByCostumerId(clientId, pageRequest);

            // Assert
            assertEquals(page.getTotalPages(), response.getTotalPages());
            assertEquals(page.getTotalPages(), response.getTotalElements());
            assertEquals(page.getSize(), response.getSize());
            assertEquals(page.getNumber(), response.getNumber());

            assertEquals(page.getContent().getFirst().getOrderId(), response.getContent().getFirst().codigoPedido());
            assertEquals(page.getContent().getFirst().getClientId(), response.getContent().getFirst().codigoCliente());
            assertEquals(page.getContent().getFirst().getPrice(), response.getContent().getFirst().total());

        }
    }

}