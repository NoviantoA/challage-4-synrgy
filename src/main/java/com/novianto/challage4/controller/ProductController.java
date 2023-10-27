package com.novianto.challage4.controller;

import com.novianto.challage4.dto.OrderDto;
import com.novianto.challage4.dto.ProductDto;
import com.novianto.challage4.entity.Order;
import com.novianto.challage4.entity.Product;
import com.novianto.challage4.repository.ProductRepository;
import com.novianto.challage4.service.ProductService;
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
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private SimpleStringUtils simpleStringUtils;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, Object>> saveProduct(@RequestBody ProductDto request) {
        return new ResponseEntity<Map<String, Object>>(productService.saveProduct(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{productId}", "/update/{productId}/"})
    public ResponseEntity<Map<String, Object>> updateProduct(@RequestBody ProductDto request, @PathVariable("productId") UUID productId) {
        return new ResponseEntity<Map<String, Object>>(productService.updateProduct(productId, request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{productId}", "/delete/{productId}/"})
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable("productId") UUID productId) {
        return new ResponseEntity<Map<String, Object>>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{productId}", "/get/{productId}/"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("productId") UUID productId) {
        return new ResponseEntity<Map<String, Object>>(productService.getProductById(productId), HttpStatus.OK);
    }

    @GetMapping("/paging-product")
    public Page<Product> getAllProductsPaging(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return productService.getAllProduct(page, size);
    }

    @GetMapping(value = {"/allProduct", "/allProduct/"})
    public ResponseEntity<Map<String, Object>> findAllProduct(@RequestParam() Integer page,
                                                              @RequestParam(required = true) Integer size,
                                                              @RequestParam(required = false) String productName,
                                                              @RequestParam(required = false) String orderby,
                                                              @RequestParam(required = false) String ordertype) {
        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);
        Page<Product> list = null;

        if (productName != null && !productName.isEmpty()) {
            list = productRepository.getByLikeName("%" + productName + "%", show_data);
        } else {
            list = productRepository.getALlPage(show_data);
        }
        Map map = new HashMap();
        map.put("data", list);
        return new ResponseEntity<Map<String, Object>>(map, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = {"/list-spec", "/list-spec/"})
    public ResponseEntity<Map<String, Object>> listProductHeaderSpec(
            @RequestParam() Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String price,
            @RequestParam(required = false) String orderby,
            @RequestParam(required = false) String ordertype) {

        Pageable show_data = simpleStringUtils.getShort(orderby, ordertype, page, size);

        Specification<Product> spec =
                ((root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (productName != null && !productName.isEmpty()) {
                        //      select  * from product p where name like '%p%' ;
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("productName")), "%" + productName.toLowerCase() + "%"));
                    }
                    if (price != null && !price.isEmpty()) {
                        // equal == select  * from employee e where  address ='jakarta'
                        predicates.add(criteriaBuilder.equal(root.get("price"), price));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                });

        //        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Product> list = productRepository.findAll(spec, show_data);

        Map map = new HashMap();
        map.put("data", list);
        return new ResponseEntity<Map<String, Object>>(map, new HttpHeaders(), HttpStatus.OK);
    }
}
