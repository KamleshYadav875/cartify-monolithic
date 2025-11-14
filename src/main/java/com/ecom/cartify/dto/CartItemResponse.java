package com.ecom.cartify.dto;

import com.ecom.cartify.entity.Product;
import com.ecom.cartify.entity.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long id;

    private UserResponse user;

    private ProductResponse product;

    private Integer quantity;

    private BigDecimal price;

}
