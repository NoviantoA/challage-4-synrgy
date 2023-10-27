package com.novianto.challage4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Jumlah tidak boleh null")
    @NotBlank(message = "Jumlah tidak boleh kosong")
    private Integer quantity;

    @NotNull(message = "Total harga tidak boleh null")
    @NotBlank(message = "Total harga tidak boleh kosong")
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
