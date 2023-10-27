package com.novianto.challage4.service.impl;

import com.novianto.challage4.dto.ProductDto;
import com.novianto.challage4.entity.Merchant;
import com.novianto.challage4.entity.Product;
import com.novianto.challage4.repository.MerchantRepository;
import com.novianto.challage4.repository.ProductRepository;
import com.novianto.challage4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public Page<Product> getAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> saveProduct(ProductDto productDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (productDto != null && productDto.getMerchant() != null) {
                Product product = new Product();
                product.setId(UUID.randomUUID());
                product.setProductName(productDto.getProductName());
                product.setPrice(productDto.getPrice());

                UUID merchantId = productDto.getMerchant().getId();
                Merchant merchant = merchantRepository.findById(merchantId).orElse(null);

                if (merchant != null) {
                    product.setMerchant(merchant.getId());
                    Product saveProduct = productRepository.save(product);

                    response.put("success", true);
                    response.put("message", "Product berhasil disimpan");
                    response.put("product", saveProduct);
                } else {
                    response.put("success", false);
                    response.put("message", "Merchant dengan ID yang diberikan tidak ditemukan");
                }
            } else {
                response.put("success", false);
                response.put("message", "Data ProductDto tidak valid");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menyimpan product");
        }
        return response;
    }

    @Override
    public Map<String, Object> updateProduct(UUID idProduct, ProductDto productDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Product> existingProduct = Optional.ofNullable(productRepository.getByIdProduct(idProduct));

            if (existingProduct.isPresent()) {
                Product newProduct = existingProduct.get();
                newProduct.setProductName(productDto.getProductName());
                newProduct.setPrice(productDto.getPrice());

                UUID merchantId = productDto.getMerchant().getId();
                Merchant merchant = merchantRepository.findById(merchantId).orElse(null);

                if (merchant != null) {
                    newProduct.setMerchant(merchant.getId());
                    Product saveProduct = productRepository.save(newProduct);

                    response.put("success", true);
                    response.put("message", "Produk berhasil diperbarui");
                    response.put("product", saveProduct);
                } else {
                    response.put("success", false);
                    response.put("message", "Merchant dengan ID yang diberikan tidak ditemukan");
                }
            } else {
                response.put("success", false);
                response.put("message", "Produk tidak ditemukan");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Gagal memperbarui produk");
        }
        return response;
    }

    @Override
    public Map<String, Object> deleteProduct(UUID idProduct) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Product> findProductOptional = Optional.ofNullable(productRepository.getByIdProduct(idProduct));

            if (findProductOptional.isPresent()) {
                Product product = findProductOptional.get();
                productRepository.delete(product);
                response.put("success", true);
                response.put("message", "Data produk ditemukan dan dihapus");
                response.put("data", product);
            } else {
                response.put("success", false);
                response.put("message", "Data produk tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal menghapus produk");
        }
        return response;
    }

    @Override
    public Map<String, Object> getProductById(UUID idProduct) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Product> findProductOptional = Optional.ofNullable(productRepository.getByIdProduct(idProduct));

            if (findProductOptional.isPresent()) {
                Product product = findProductOptional.get();
                response.put("success", true);
                response.put("message", "Data produk ditemukan");
                response.put("data", product);
            } else {
                response.put("success", false);
                response.put("message", "Data produk tidak ditemukan");
            }
        } catch (DataAccessException e) {
            response.put("success", false);
            response.put("message", "Gagal mengambil data produk");
        }
        return response;
    }
}
