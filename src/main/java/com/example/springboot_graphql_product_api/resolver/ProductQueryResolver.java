package com.example.springboot_graphql_product_api.resolver;

import com.example.springboot_graphql_product_api.dto.ProductPage;
import com.example.springboot_graphql_product_api.enums.ProductCategory;
import com.example.springboot_graphql_product_api.enums.ProductSortBy;
import com.example.springboot_graphql_product_api.enums.ProductStatus;
import com.example.springboot_graphql_product_api.model.Product;
import com.example.springboot_graphql_product_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductQueryResolver {

    private final ProductService productService;

    @QueryMapping
    public ProductPage products(
            @Argument int page,
            @Argument int size,
            @Argument ProductSortBy sortBy) {
        return productService.getAllProducts(page, size, sortBy);
    }

    @QueryMapping
    public Product product(@Argument Long id) {
        return productService.getProductById(id);
    }

    @QueryMapping
    public List<Product> searchProducts(@Argument String name) {
        return productService.searchProductsByName(name);
    }

    @QueryMapping
    public List<Product> productsByCategory(@Argument ProductCategory category) {
        return productService.getProductsByCategory(category);
    }

    @QueryMapping
    public List<Product> productsByStatus(@Argument ProductStatus status) {
        return productService.getProductsByStatus(status);
    }

    @QueryMapping
    public List<Product> productsByPriceRange(
            @Argument BigDecimal minPrice,
            @Argument BigDecimal maxPrice) {
        return productService.getProductsByPriceRange(minPrice, maxPrice);
    }

    @QueryMapping
    public List<Product> activeProductsUnderPrice(@Argument BigDecimal maxPrice) {
        return productService.getActiveProductsUnderPrice(maxPrice);
    }
}
