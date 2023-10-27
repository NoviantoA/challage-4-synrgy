package com.novianto.challage4.service;

import com.novianto.challage4.dto.OrderDto;
import com.novianto.challage4.dto.ProductDto;
import com.novianto.challage4.entity.Order;
import com.novianto.challage4.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductService {

    Page<Product> getAllProduct(int page, int size);

    Map<String, Object> saveProduct(ProductDto productDto);

    Map<String, Object> updateProduct(UUID idProduct, ProductDto productDto);

    Map<String, Object> deleteProduct(UUID idProduct);

    Map<String, Object> getProductById(UUID idProduct);
}
