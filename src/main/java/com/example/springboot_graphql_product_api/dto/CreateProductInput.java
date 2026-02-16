package com.example.springboot_graphql_product_api.dto;

import com.example.springboot_graphql_product_api.enums.ProductStatus;
import com.example.springboot_graphql_product_api.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductInput {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private ProductType type;
    private ProductStatus status;
    private Long brandId;
    private Set<Long> categoryIds;
    private String imageUrl;
}
