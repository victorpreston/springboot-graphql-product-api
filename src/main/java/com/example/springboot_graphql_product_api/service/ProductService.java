package com.example.springboot_graphql_product_api.service;

import com.example.springboot_graphql_product_api.dto.CreateProductInput;
import com.example.springboot_graphql_product_api.dto.ProductPage;
import com.example.springboot_graphql_product_api.dto.UpdateProductInput;
import com.example.springboot_graphql_product_api.enums.ProductCategory;
import com.example.springboot_graphql_product_api.enums.ProductSortBy;
import com.example.springboot_graphql_product_api.enums.ProductStatus;
import com.example.springboot_graphql_product_api.exception.ProductNotFoundException;
import com.example.springboot_graphql_product_api.model.Product;
import com.example.springboot_graphql_product_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductPage getAllProducts(int page, int size, ProductSortBy sortBy) {
        Pageable pageable = PageRequest.of(page, size, getSortOrder(sortBy));
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return new ProductPage(
            productPage.getContent(),
            productPage.getTotalElements(),
            productPage.getTotalPages(),
            productPage.getNumber(),
            productPage.getSize()
        );
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(ProductCategory category) {
        return productRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional(readOnly = true)
    public List<Product> getActiveProductsUnderPrice(BigDecimal maxPrice) {
        return productRepository.findActiveProductsUnderPrice(maxPrice);
    }

    @Transactional
    public Product createProduct(CreateProductInput input) {
        Product product = new Product();
        product.setName(input.getName());
        product.setDescription(input.getDescription());
        product.setPrice(input.getPrice());
        product.setStock(input.getStock());
        product.setCategory(input.getCategory());
        product.setStatus(input.getStatus());
        product.setImageUrl(input.getImageUrl());
        
        Product savedProduct = productRepository.save(product);
        log.info("Created product with id: {}", savedProduct.getId());
        return savedProduct;
    }

    @Transactional
    public Product updateProduct(Long id, UpdateProductInput input) {
        Product product = getProductById(id);
        
        if (input.getName() != null) product.setName(input.getName());
        if (input.getDescription() != null) product.setDescription(input.getDescription());
        if (input.getPrice() != null) product.setPrice(input.getPrice());
        if (input.getStock() != null) product.setStock(input.getStock());
        if (input.getCategory() != null) product.setCategory(input.getCategory());
        if (input.getStatus() != null) product.setStatus(input.getStatus());
        if (input.getImageUrl() != null) product.setImageUrl(input.getImageUrl());
        
        Product updatedProduct = productRepository.save(product);
        log.info("Updated product with id: {}", updatedProduct.getId());
        return updatedProduct;
    }

    @Transactional
    public boolean deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Deleted product with id: {}", id);
        return true;
    }

    @Transactional
    public Product updateProductStock(Long id, Integer stock) {
        Product product = getProductById(id);
        product.setStock(stock);
        
        if (stock == 0) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        } else if (product.getStatus() == ProductStatus.OUT_OF_STOCK) {
            product.setStatus(ProductStatus.ACTIVE);
        }
        
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProductStatus(Long id, ProductStatus status) {
        Product product = getProductById(id);
        product.setStatus(status);
        return productRepository.save(product);
    }

    private Sort getSortOrder(ProductSortBy sortBy) {
        if (sortBy == null) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        
        return switch (sortBy) {
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "name");
            case NAME_DESC -> Sort.by(Sort.Direction.DESC, "name");
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "price");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "price");
            case CREATED_AT_ASC -> Sort.by(Sort.Direction.ASC, "createdAt");
            case CREATED_AT_DESC -> Sort.by(Sort.Direction.DESC, "createdAt");
            case STOCK_ASC -> Sort.by(Sort.Direction.ASC, "stock");
            case STOCK_DESC -> Sort.by(Sort.Direction.DESC, "stock");
        };
    }
}
