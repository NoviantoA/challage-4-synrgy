package com.novianto.challage4.controller;

import com.novianto.challage4.dto.OrderDto;
import com.novianto.challage4.entity.Order;
import com.novianto.challage4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody OrderDto request) {
        return new ResponseEntity<Map<String, Object>>(orderService.saveOrder(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{orderId}", "/update/{orderId}/"})
    public ResponseEntity<Map<String, Object>> updateOrder(@RequestBody OrderDto request, @PathVariable("orderId") UUID orderId) {
        return new ResponseEntity<Map<String, Object>>(orderService.updateOrder(orderId, request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{orderId}", "/delete/{orderId}/"})
    public ResponseEntity<Map<String, Object>> deleteOrder(@PathVariable("orderId") UUID orderId) {
        return new ResponseEntity<Map<String, Object>>(orderService.deleteOrder(orderId), HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{orderId}", "/get/{orderId}/"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("orderId") UUID orderId) {
        return new ResponseEntity<Map<String, Object>>(orderService.getOrderById(orderId), HttpStatus.OK);
    }

    @GetMapping(value = {"/allOrder", "/allOrder/"})
    public ResponseEntity<List<Order>> findAllOrder(){
        return new ResponseEntity<>(orderService.getAllOrder(), HttpStatus.OK);
    }
}
