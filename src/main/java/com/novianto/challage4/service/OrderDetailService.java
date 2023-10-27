package com.novianto.challage4.service;

import com.novianto.challage4.dto.OrderDetailDto;
import com.novianto.challage4.entity.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface OrderDetailService {

    Page<OrderDetail> getAllOrderDetail(Pageable pageable);

    Map<String, Object> saveOrderDetail(OrderDetailDto orderDetailDto);

    Map<String, Object> updateOrderDetail(UUID idOrderDetail, OrderDetailDto orderDetailDto);

    Map<String, Object> deleteOrderDetail(UUID idOrderDetail);

    Map<String, Object> getOrderDetailById(UUID idOrderDetail);
}
