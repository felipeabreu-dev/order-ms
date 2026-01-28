package dev.felipeabreu.btgpactual.orderms.controller;

import dev.felipeabreu.btgpactual.orderms.dto.ApiResponse;
import dev.felipeabreu.btgpactual.orderms.dto.OrderResponse;
import dev.felipeabreu.btgpactual.orderms.dto.PaginationResponse;
import dev.felipeabreu.btgpactual.orderms.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/costumers/{clientId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> listOrders(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                 @PathVariable Long clientId) {

        var pageResponse = orderService.findAllByCostumerId(clientId, PageRequest.of(page, pageSize));


        return ResponseEntity.ok(
                new ApiResponse<>(
                        pageResponse.getContent(),
                        PaginationResponse.fromPage(pageResponse)
                )
        );

    }
}
