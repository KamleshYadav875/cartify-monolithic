package com.ecom.cartify.service;

import com.ecom.cartify.dto.CartItemRequest;
import com.ecom.cartify.entity.CartItem;
import com.ecom.cartify.entity.Product;
import com.ecom.cartify.entity.User;
import com.ecom.cartify.repository.CartItemRepository;
import com.ecom.cartify.repository.ProductRepository;
import com.ecom.cartify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if(productOpt.isEmpty()){
            return false;
        }

        Product product = productOpt.get();

        if(product.getStockQuantity() < request.getQuantity()){
            return false;
        }

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isEmpty()){
             return false;
        }

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if(ObjectUtils.isEmpty(existingCartItem)){
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        else {
            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }
        return true;
    }


    public boolean deleteItemFromCart(String userId, Long productId) {

        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));

        if(userOpt.isPresent() && productOpt.isPresent()){
            CartItem cartItem = cartItemRepository.findByUserAndProduct(userOpt.get(), productOpt.get());
            cartItemRepository.delete(cartItem);
            return true;
        }

        return false;

    }

    public List<CartItem> getCart(String userId) {

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if(userOpt.isPresent()){
            return cartItemRepository.findByUser(userOpt.get());
        }
        return null;
    }
}
