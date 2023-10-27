package com.novianto.challage4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Nama produk tidak boleh null")
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String productName;

    @NotNull(message = "Harga produk tidak boleh null")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "merchantId", referencedColumnName = "id")
    private Merchant merchant;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public void setMerchant(UUID merchantId) {
        Merchant merchant = new Merchant();
        merchant.setId(merchantId);
        this.merchant = merchant;
    }
}
