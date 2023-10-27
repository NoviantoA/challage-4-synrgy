package com.novianto.challage4.dto;

import com.novianto.challage4.entity.Merchant;
import lombok.Data;

@Data
public class ProductDto {
    private String productName;
    private Double price;
    private Merchant merchant;
}
