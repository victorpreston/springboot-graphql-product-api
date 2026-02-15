package com.example.springboot_graphql_product_api.resolver;

import com.example.springboot_graphql_product_api.dto.CreateProductInput;
import com.example.springboot_graphql_product_api.dto.UpdateProductInput;
import com.example.springboot_graphql_product_api.enums.ProductStatus;
import com.example.springboot_graphql_product_api.model.Product;
import com.example.springboot_graphql_product_api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductMutationResolver {

    private final ProductService productService;

    @MutationMapping
    public Product createProduct(@Argument CreateProductInput input) {
        return productService.createProduct(input);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument UpdateProductInput input) {
        return productService.updateProduct(id, input);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    @MutationMapping
    public Product updateProductStock(@Argument Long id, @Argument Integer stock) {
        return productService.updateProductStock(id, stock);
    }

    @MutationMapping
    public Product updateProductStatus(@Argument Long id, @Argument ProductStatus status) {
        return productService.updateProductStatus(id, status);
    }
}
