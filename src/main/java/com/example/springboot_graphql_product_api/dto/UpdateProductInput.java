package com.example.springboot_graphql_product_api.dto;

import com.example.springboot_graphql_product_api.enums.ProductCategory;
import com.example.springboot_graphql_product_api.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductInput {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private ProductCategory category;
    private ProductStatus status;
    private String imageUrl;
}
