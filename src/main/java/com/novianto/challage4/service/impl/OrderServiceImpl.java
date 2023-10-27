package com.novianto.challage4.service.impl;

import com.novianto.challage4.dto.OrderDto;
import com.novianto.challage4.entity.Order;
import com.novianto.challage4.entity.User;
import com.novianto.challage4.repository.OrderRepository;
import com.novianto.challage4.repository.UserRepository;
import com.novianto.challage4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Map<String, Object> saveOrder(OrderDto orderDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (orderDto != null && orderDto.getUser() != null) {
                Order order = new Order();
                order.setId(UUID.randomUUID());
                order.setOrderTime(orderDto.getOrderTime());
                order.setDestinationAddress(orderDto.getDestinationAddress());
                order.setCompleted(false);

                UUID userId = orderDto.getUser().getId();
                User user = userRepository.findById(userId).orElse(null);

                if (user != null) {
                    order.setUser(user.getId());
                    Order saveOrder = orderRepository.save(order);

                    response.put("success", true);
                    response.put("message", "Order berhasil disimpan");
                    response.put("order", saveOrder);

                    logger.info("Order berhasil disimpan: " + saveOrder.getId());
                } else {
                    response.put("success", false);
                    response.put("message", "User dengan ID yang diberikan tidak ditemukan");

                    logger.error("User tidak ditemukan dengan ID: " + userId);
                }
            } else {
                response.put("success", false);
                response.put("message", "Data OrderDto tidak valid");

                logger.error("Data OrderDto tidak valid");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menyimpan order");

            logger.error("Gagal menyimpan order: " + e.getMessage(), e);
        }
        return response;
    }

    @Override
    public Map<String, Object> updateOrder(UUID idOrder, OrderDto orderDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Order> existingOrder = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));

            if (existingOrder.isPresent()) {
                Order newOrder = existingOrder.get();
                newOrder.setOrderTime(orderDto.getOrderTime());
                newOrder.setDestinationAddress(orderDto.getDestinationAddress());
                newOrder.setCompleted(orderDto.isCompleted());

                UUID userId = orderDto.getUser().getId();
                User user = userRepository.findById(userId).orElse(null);

                if (user != null) {
                    newOrder.setUser(user.getId());
                    Order saveOrder = orderRepository.save(newOrder);

                    response.put("success", true);
                    response.put("message", "Order berhasil diperbarui");
                    response.put("order", saveOrder);

                    logger.info("Order berhasil diperbarui: " + saveOrder.getId());
                } else {
                    response.put("success", false);
                    response.put("message", "User dengan ID yang diberikan tidak ditemukan");

                    logger.error("User tidak ditemukan dengan ID: " + userId);
                }
            } else {
                response.put("success", false);
                response.put("message", "Order tidak ditemukan");

                logger.error("Order tidak ditemukan dengan ID: " + idOrder);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Gagal memperbarui order");

            logger.error("Gagal memperbarui order: " + e.getMessage(), e);
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteOrder(UUID idOrder) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Order> findOrderOptional = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));

            if (findOrderOptional.isPresent()) {
                Order order = findOrderOptional.get();
                orderRepository.delete(order);
                response.put("success", true);
                response.put("message", "Data order ditemukan dan dihapus");
                response.put("data", order);

                logger.info("Data order ditemukan dan dihapus: " + idOrder);
            } else {
                response.put("success", false);
                response.put("message", "Data order tidak ditemukan");

                logger.error("Data order tidak ditemukan: " + idOrder);
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menghapus order");

            logger.error("Gagal menghapus order: " + e.getMessage(), e);
        }
        return response;
    }

    @Override
    public Map<String, Object> getOrderById(UUID idOrder) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Order> findOrderOptional = Optional.ofNullable(orderRepository.getByIdOrder(idOrder));
            if (findOrderOptional.isPresent()) {
                Order order = findOrderOptional.get();
                response.put("success", true);
                response.put("message", "Data order ditemukan");
                response.put("data", order);

                logger.info("Data order ditemukan: " + idOrder);
            } else {
                response.put("success", false);
                response.put("message", "Data order tidak ditemukan");

                logger.error("Data order tidak ditemukan: " + idOrder);
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal mengambil data order");

            logger.error("Gagal mengambil data order: " + e.getMessage(), e);
        }
        return response;
    }
}
