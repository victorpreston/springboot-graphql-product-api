package com.example.springboot_graphql_product_api.resolver;

import com.example.springboot_graphql_product_api.dto.CreateBrandInput;
import com.example.springboot_graphql_product_api.dto.CreateCategoryInput;
import com.example.springboot_graphql_product_api.dto.UpdateBrandInput;
import com.example.springboot_graphql_product_api.dto.UpdateCategoryInput;
import com.example.springboot_graphql_product_api.model.Brand;
import com.example.springboot_graphql_product_api.model.Category;
import com.example.springboot_graphql_product_api.service.BrandService;
import com.example.springboot_graphql_product_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BrandCategoryMutationResolver {

    private final BrandService brandService;
    private final CategoryService categoryService;

    @MutationMapping
    public Brand createBrand(@Argument CreateBrandInput input) {
        return brandService.createBrand(input);
    }

    @MutationMapping
    public Brand updateBrand(@Argument Long id, @Argument UpdateBrandInput input) {
        return brandService.updateBrand(id, input);
    }

    @MutationMapping
    public Boolean deleteBrand(@Argument Long id) {
        return brandService.deleteBrand(id);
    }

    @MutationMapping
    public Category createCategory(@Argument CreateCategoryInput input) {
        return categoryService.createCategory(input);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument UpdateCategoryInput input) {
        return categoryService.updateCategory(id, input);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}
