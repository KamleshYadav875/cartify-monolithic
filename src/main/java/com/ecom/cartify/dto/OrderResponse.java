package com.ecom.cartify.dto;

import com.ecom.cartify.entity.OrderItems;
import com.ecom.cartify.entity.OrderStatus;
import com.ecom.cartify.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemsDTO> items;
    private LocalDateTime createdAt;
}
