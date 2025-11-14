package com.ecom.cartify.repository;

import com.ecom.cartify.entity.CartItem;
import com.ecom.cartify.entity.Product;
import com.ecom.cartify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
