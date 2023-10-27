package com.novianto.challage4.service.impl;

import com.novianto.challage4.dto.OrderDetailDto;
import com.novianto.challage4.entity.Order;
import com.novianto.challage4.entity.OrderDetail;
import com.novianto.challage4.entity.Product;
import com.novianto.challage4.repository.OrderDetailRepository;
import com.novianto.challage4.repository.OrderRepository;
import com.novianto.challage4.repository.ProductRepository;
import com.novianto.challage4.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<OrderDetail> getAllOrderDetail(Pageable pageable) {
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> saveOrderDetail(OrderDetailDto orderDetailDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (orderDetailDto != null) {
                UUID orderId = orderDetailDto.getOrder().getId();
                UUID productId = orderDetailDto.getProduct().getId();

                Optional<Order> optionalOrder = orderRepository.findById(orderId);
                Optional<Product> optionalProduct = productRepository.findById(productId);

                if (optionalOrder.isPresent() && optionalProduct.isPresent()) {
                    Order order = optionalOrder.get();
                    Product product = optionalProduct.get();

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setId(UUID.randomUUID());
                    orderDetail.setQuantity(orderDetailDto.getQuantity());
                    orderDetail.setTotalPrice(orderDetailDto.getTotalPrice());
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(product);

                    Order saveOrder = orderRepository.save(order);

                    response.put("success", true);
                    response.put("message", "Order Detail berhasil disimpan");
                    response.put("orderDetail", saveOrder);
                } else {
                    response.put("success", false);
                    response.put("message", "Order atau Product tidak ditemukan");
                }
            } else {
                response.put("success", false);
                response.put("message", "Data OrderDetail tidak valid");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menyimpan order");
        }
        return response;
    }

    @Override
    public Map<String, Object> updateOrderDetail(UUID idOrderDetail, OrderDetailDto orderDetailDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<OrderDetail> existingOrderDetailOptional = Optional.ofNullable(orderDetailRepository.getByIdOrderDetail(idOrderDetail));

            if (existingOrderDetailOptional.isPresent()) {
                OrderDetail existingOrderDetail = existingOrderDetailOptional.get();
                existingOrderDetail.setQuantity(orderDetailDto.getQuantity());
                existingOrderDetail.setTotalPrice(orderDetailDto.getTotalPrice());

                UUID orderId = orderDetailDto.getOrder().getId();
                UUID productId = orderDetailDto.getProduct().getId();

                Order order = orderRepository.findById(orderId).orElse(null);
                Product product = productRepository.findById(productId).orElse(null);

                if (order != null && product != null) {
                    existingOrderDetail.setOrder(order);
                    existingOrderDetail.setProduct(product);

                    OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);

                    response.put("success", true);
                    response.put("message", "Order Detail berhasil diperbarui");
                    response.put("orderDetail", updatedOrderDetail);
                } else {
                    response.put("success", false);
                    response.put("message", "Order atau Product dengan ID yang diberikan tidak ditemukan");
                }
            } else {
                response.put("success", false);
                response.put("message", "Order Detail tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal memperbarui Order Detail");
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteOrderDetail(UUID idOrderDetail) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<OrderDetail> findOrderDetailOptional = Optional.ofNullable(orderDetailRepository.getByIdOrderDetail(idOrderDetail));

            if (findOrderDetailOptional.isPresent()) {
                OrderDetail orderDetail = findOrderDetailOptional.get();
                orderDetailRepository.delete(orderDetail);
                response.put("success", true);
                response.put("message", "Data order detail ditemukan dan dihapus");
                response.put("data", orderDetail);
            } else {
                response.put("success", false);
                response.put("message", "Data order detail tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menghapus order detail");
        }
        return response;
    }

    @Override
    public Map<String, Object> getOrderDetailById(UUID idOrderDetail) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<OrderDetail> findOrderDetailOptional = Optional.ofNullable(orderDetailRepository.getByIdOrderDetail(idOrderDetail));
            if (findOrderDetailOptional.isPresent()) {
                OrderDetail orderDetail = findOrderDetailOptional.get();
                response.put("success", true);
                response.put("message", "Data order detail ditemukan");
                response.put("data", orderDetail);
            } else {
                response.put("success", false);
                response.put("message", "Data order detail tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal mengambil data order detail");
        }
        return response;
    }
}
