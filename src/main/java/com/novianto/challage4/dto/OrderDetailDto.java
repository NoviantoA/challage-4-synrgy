package com.novianto.challage4.dto;

import com.novianto.challage4.entity.Order;
import com.novianto.challage4.entity.Product;
import lombok.Data;

@Data
public class OrderDetailDto {
    private Integer quantity;
    private Double totalPrice;
    private Order order;
    private Product product;
}
