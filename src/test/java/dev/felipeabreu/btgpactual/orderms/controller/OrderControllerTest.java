package dev.felipeabreu.btgpactual.orderms.controller;

import dev.felipeabreu.btgpactual.orderms.factory.OrderResponseFactory;
import dev.felipeabreu.btgpactual.orderms.service.OrderService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock // Cria um Mock da classe OrderService para que o nosso teste não utilize a implementação real
    OrderService orderService;

    @InjectMocks // Injetamos os mocks criados na classe que necessita deles
    OrderController orderController;

    @Captor
    ArgumentCaptor<Long> clientIdCaptor;
    @Captor
    ArgumentCaptor<PageRequest> pageRequestCaptor;

    @Nested // Indica que nesta classe haverá testes unitarios
    class ListOrders {

        @Test
        void shouldReturnHttpOk() {
            // ARRANGE - Prepara todos os mocks para a execução
            Integer page = 0;
            Integer pageSize = 10;
            Long clientId = 1L;

            // É necessário mockar também o metodo que o orderController.listOrders utiliza, pois quando ele for executar ele precisa desses parametros
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCostumerId(anyLong(), any());

            // ACT - Executar o metodo a ser testado
            var response = orderController.listOrders(page, pageSize, clientId);

            // ASSERT - Verifica se a execução ocorreu corretamente
            assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        }

        @Test
        void shouldTestCorrectParametersToService() {
            // ARRANGE - Prepara todos os mocks para a execução
            Integer page = 0;
            Integer pageSize = 10;
            Long clientId = 1L;

            // É necessário mockar também o metodo que o orderController.listOrders utiliza, pois quando ele for executar ele precisa desses parametros
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCostumerId(clientIdCaptor.capture(), pageRequestCaptor.capture());

            // ACT - Executar o metodo a ser testado
            var response = orderController.listOrders(page, pageSize, clientId);

            // ASSERT - Verifica se a execução ocorreu corretamente
            assertEquals(1, clientIdCaptor.getAllValues().size());
            assertEquals(clientId, clientIdCaptor.getValue());
            assertEquals(page, pageRequestCaptor.getValue().getPageNumber());
            assertEquals(pageSize, pageRequestCaptor.getValue().getPageSize());
        }

        @Test
        void shouldReturnResponseBodyCorrectly() {
            // ARRANGE - Prepara todos os mocks para a execução
            Integer page = 0;
            Integer pageSize = 10;
            Long clientId = 1L;
            var pagination = OrderResponseFactory.buildWithOneItem();

            // É necessário mockar também o metodo que o orderController.listOrders utiliza, pois quando ele for executar ele precisa desses parametros
            doReturn(OrderResponseFactory.buildWithOneItem())
                    .when(orderService).findAllByCostumerId(anyLong(), any());

            // ACT - Executar o metodo a ser testado
            var response = orderController.listOrders(page, pageSize, clientId);

            // ASSERT - Verifica se a execução ocorreu corretamente
            assertNotNull(response);
            assertNotNull(response.getBody());
            assertNotNull(response.getBody().data());
            assertNotNull(response.getBody().pagination());


            assertEquals(pagination.getTotalElements(), response.getBody().pagination().totalElements());
            assertEquals(pagination.getTotalPages(), response.getBody().pagination().totalPages());
            assertEquals(pagination.getNumber(), response.getBody().pagination().page());
            assertEquals(pagination.getSize(), response.getBody().pagination().pageSize());

            assertEquals(pagination.getContent(), response.getBody().data());
        }
    }

}