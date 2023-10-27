package com.novianto.challage4.controller;

import com.novianto.challage4.dto.OrderDetailDto;
import com.novianto.challage4.entity.OrderDetail;
import com.novianto.challage4.repository.OrderDetailRepository;
import com.novianto.challage4.service.OrderDetailService;
import com.novianto.challage4.util.SimpleStringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/order-detail")
public class OrderDetailController {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private SimpleStringUtils simpleStringUtils;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, Object>> saveOrderDetail(@RequestBody OrderDetailDto request) {
        return new ResponseEntity<Map<String, Object>>(orderDetailService.saveOrderDetail(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{orderDetailId}", "/update/{orderDetailId}/"})
    public ResponseEntity<Map<String, Object>> updateOrderDetail(@RequestBody OrderDetailDto request, @PathVariable("orderDetailId") UUID orderDetailId) {
        return new ResponseEntity<Map<String, Object>>(orderDetailService.updateOrderDetail(orderDetailId, request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{orderDetailId}", "/delete/{orderDetailId}/"})
    public ResponseEntity<Map<String, Object>> deleteOrderDetail(@PathVariable("orderDetailId") UUID orderDetailId) {
        return new ResponseEntity<Map<String, Object>>(orderDetailService.deleteOrderDetail(orderDetailId), HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{orderDetailId}", "/get/{orderDetailId}/"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("orderDetailId") UUID orderDetailId) {
        return new ResponseEntity<Map<String, Object>>(orderDetailService.getOrderDetailById(orderDetailId), HttpStatus.OK);
    }

    @GetMapping("/paging-order-detail")
    public Page<OrderDetail> getAllOrderDetailsPaging(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderDetailService.getAllOrderDetail(pageable);
    }

    @GetMapping(value = {"/all-order-detail", "/all-order-detail/"})
    public ResponseEntity<Map<String, Object>> findAllOrderDetail(@RequestParam() Integer page,
                                                                  @RequestParam(required = true) Integer size,
                                                                  @RequestParam(required = false) Double totalPrize,
                                                                  @RequestParam(required = false) String orderby,
                                                                  @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<OrderDetail> list = null;

        if (totalPrize != null) {
            list = orderDetailRepository.getByLikeTotalPrice(Double.valueOf("%" + totalPrize + "%"), show_data);
        } else {
            list = orderDetailRepository.getALlPage(show_data);
        }
        Map map = new HashMap();
        map.put("data", list);
        return new ResponseEntity<Map<String, Object>>(map, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-spec", "/list-spec/"})
    public ResponseEntity<Map<String, Object>> listOrderDetailHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) Integer quantity,
            @RequestParam(required = false) Double totalPrice,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<OrderDetail> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (quantity != null) {
                        //      select  * from order_details od where quantity like '%od%' ;
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("quantity")), "%" + quantity + "%"));
                    }
                    if (totalPrice != null) {
                        // equal == select  * from order_details od where  total_price = 2000.00
                        predicates.add(criteriaBuilder.equal(root.get("totalPrice"), totalPrice));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        //        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDetail> list = orderDetailRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data", list);
        return new ResponseEntity<Map<String, Object>>(map, new HttpHeaders(), HttpStatus.OK);
    }
}
