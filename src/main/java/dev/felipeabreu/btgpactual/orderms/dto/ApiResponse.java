package dev.felipeabreu.btgpactual.orderms.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record ApiResponse<T>(
        List<T> data,
        PaginationResponse pagination) {

}
