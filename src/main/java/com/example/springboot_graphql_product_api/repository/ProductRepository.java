package com.example.springboot_graphql_product_api.repository;

import com.example.springboot_graphql_product_api.enums.ProductStatus;
import com.example.springboot_graphql_product_api.enums.ProductType;
import com.example.springboot_graphql_product_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByType(ProductType type);

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    List<Product> findByBrandId(Long brandId);

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p WHERE p.price <= :maxPrice AND p.status = 'ACTIVE'")
    List<Product> findActiveProductsUnderPrice(@Param("maxPrice") BigDecimal maxPrice);
}
