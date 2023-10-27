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
@Table(name = "merchants")
public class Merchant {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Nama merchant tidak boleh null")
    @NotBlank(message = "Nama merchant tidak boleh kosong")
    private String merchantName;

    @NotNull(message = "Lokasi merchant tidak boleh null")
    @NotBlank(message = "Lokasi merchant tidak boleh kosong")
    private String merchantLocation;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "merchant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
