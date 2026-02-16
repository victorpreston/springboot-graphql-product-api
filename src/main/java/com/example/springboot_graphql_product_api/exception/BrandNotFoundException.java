package com.example.springboot_graphql_product_api.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(Long id) {
        super("Brand not found with id: " + id);
    }
    
    public BrandNotFoundException(String slug) {
        super("Brand not found with slug: " + slug);
    }
}
