package com.novianto.challage4.dto;

import com.novianto.challage4.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private Date orderTime;
    private String destinationAddress;
    private boolean completed;
    private User user;
}
