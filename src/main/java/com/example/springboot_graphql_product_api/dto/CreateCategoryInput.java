package com.example.springboot_graphql_product_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryInput {
    private String name;
    private String slug;
    private String description;
    private Long parentId;
}
