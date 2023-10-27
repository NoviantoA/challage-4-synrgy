package com.novianto.challage4.service;

import com.novianto.challage4.dto.MerchantDto;
import com.novianto.challage4.dto.OrderDto;
import com.novianto.challage4.entity.Merchant;
import com.novianto.challage4.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    List<Order> getAllOrder();

    Map<String, Object> saveOrder(OrderDto orderDto);

    Map<String, Object> updateOrder(UUID idOrder, OrderDto orderDto);

    Map<String, Object> deleteOrder(UUID idOrder);

    Map<String, Object> getOrderById(UUID idOrder);
}
