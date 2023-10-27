package com.novianto.challage4.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Tanggal order tidak boleh null")
    @NotBlank(message = "Tanggal order tidak boleh kosong")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date orderTime;

    @NotNull(message = "Destinasi alamat tidak boleh null")
    @NotBlank(message = "Destinasi alamat tidak boleh kosong")
    private String destinationAddress;

    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;

    public void setUser(UUID userId) {
        User user = new User();
        user.setId(userId);
        this.user = user;
    }
}
