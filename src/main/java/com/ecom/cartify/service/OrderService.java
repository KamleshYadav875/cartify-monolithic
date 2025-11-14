package com.ecom.cartify.service;

import com.ecom.cartify.dto.OrderItemsDTO;
import com.ecom.cartify.dto.OrderResponse;
import com.ecom.cartify.entity.CartItem;
import com.ecom.cartify.entity.Order;
import com.ecom.cartify.entity.OrderItems;
import com.ecom.cartify.entity.User;
import com.ecom.cartify.repository.OrderRepository;
import com.ecom.cartify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;

    @Transactional
    public Optional<OrderResponse> createOrder(String userId) {

        // Validate for cart items
        List<CartItem> cartItemList = cartService.getCart(userId);

        if(cartItemList.isEmpty()){
            return Optional.empty();
        }

        // Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();
        // Calculate total price
        BigDecimal totalPrice = cartItemList.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalPrice);

        List<OrderItems> orderItems = cartItemList.stream()
                .map(item -> new OrderItems(
                        null,
                        order,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice()
                        ) ).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // Clear the Cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getItems().stream()
                        .map(orderItems -> new OrderItemsDTO(
                                orderItems.getId(),
                                orderItems.getProduct().getId(),
                                orderItems.getQuantity(),
                                orderItems.getPrice(),
                                orderItems.getPrice().multiply(new BigDecimal(orderItems.getQuantity()))
                        )).toList(),
                savedOrder.getCreatedAt()
        );
    }
}
