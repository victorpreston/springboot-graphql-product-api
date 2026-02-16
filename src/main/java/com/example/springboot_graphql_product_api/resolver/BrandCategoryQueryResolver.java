package com.example.springboot_graphql_product_api.resolver;

import com.example.springboot_graphql_product_api.model.Brand;
import com.example.springboot_graphql_product_api.model.Category;
import com.example.springboot_graphql_product_api.service.BrandService;
import com.example.springboot_graphql_product_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BrandCategoryQueryResolver {

    private final BrandService brandService;
    private final CategoryService categoryService;

    @QueryMapping
    public List<Brand> brands() {
        return brandService.getAllBrands();
    }

    @QueryMapping
    public Brand brand(@Argument Long id) {
        return brandService.getBrandById(id);
    }

    @QueryMapping
    public Brand brandBySlug(@Argument String slug) {
        return brandService.getBrandBySlug(slug);
    }

    @QueryMapping
    public List<Category> categories() {
        return categoryService.getAllCategories();
    }

    @QueryMapping
    public Category category(@Argument Long id) {
        return categoryService.getCategoryById(id);
    }

    @QueryMapping
    public Category categoryBySlug(@Argument String slug) {
        return categoryService.getCategoryBySlug(slug);
    }

    @QueryMapping
    public List<Category> rootCategories() {
        return categoryService.getRootCategories();
    }
}
