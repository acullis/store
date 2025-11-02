package com.example.store.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private OrderCustomerDTO customer;
    private OrderProductDTO product;
}
