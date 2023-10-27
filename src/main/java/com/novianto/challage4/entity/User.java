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
@Table(name = "users")
public class User {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;

    @NotNull(message = "Username tidak boleh null")
    @NotBlank(message = "Username tidak boleh kosong")
    private String username;

    @NotNull(message = "Email tidak boleh null")
    @NotBlank(message = "Email tidak boleh kosong")
    private String emailAddress;

    @NotNull(message = "Password tidak boleh null")
    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;
}
