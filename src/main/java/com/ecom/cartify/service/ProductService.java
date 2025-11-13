package com.ecom.cartify.service;

import com.ecom.cartify.dto.ProductRequest;
import com.ecom.cartify.dto.ProductResponse;
import com.ecom.cartify.entity.Product;
import com.ecom.cartify.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        updateProductFromRequest(product, request);
        Product savedProduct = productRepository.save(product);
        return mapToProductResponse(savedProduct);
    }


    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setActive(savedProduct.getActive());
        return  response;
    }

    private void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
    }


    public List<ProductResponse> fetchAllProducts() {
        return productRepository.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse fetchProducts(Long id){
        return productRepository.findById(id).map(this::mapToProductResponse).orElseThrow(() -> new RuntimeException("Product not found with id"));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        return productRepository.findById(id).map(p -> {
            updateProductFromRequest(p, request);
            Product savedProduct = productRepository.save(p);
            return mapToProductResponse(savedProduct);
        }).orElseThrow(() -> new RuntimeException("Product not found with id "+id));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id "+id));
        product.setActive(false);
        productRepository.save(product);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.searchProducts(keyword).stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }
}
