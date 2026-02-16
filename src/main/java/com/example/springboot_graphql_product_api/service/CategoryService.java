package com.example.springboot_graphql_product_api.service;

import com.example.springboot_graphql_product_api.dto.CreateCategoryInput;
import com.example.springboot_graphql_product_api.dto.UpdateCategoryInput;
import com.example.springboot_graphql_product_api.exception.CategoryNotFoundException;
import com.example.springboot_graphql_product_api.model.Category;
import com.example.springboot_graphql_product_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Category getCategoryBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
            .orElseThrow(() -> new CategoryNotFoundException(slug));
    }

    @Transactional(readOnly = true)
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategoriesWithChildren();
    }

    @Transactional
    public Category createCategory(CreateCategoryInput input) {
        if (categoryRepository.existsBySlug(input.getSlug())) {
            throw new IllegalArgumentException("Category with slug already exists: " + input.getSlug());
        }

        Category category = new Category();
        category.setName(input.getName());
        category.setSlug(input.getSlug());
        category.setDescription(input.getDescription());

        if (input.getParentId() != null) {
            Category parent = getCategoryById(input.getParentId());
            category.setParent(parent);
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("Created category with id: {}", savedCategory.getId());
        return savedCategory;
    }

    @Transactional
    public Category updateCategory(Long id, UpdateCategoryInput input) {
        Category category = getCategoryById(id);

        if (input.getName() != null) category.setName(input.getName());
        if (input.getSlug() != null) {
            if (!category.getSlug().equals(input.getSlug()) && categoryRepository.existsBySlug(input.getSlug())) {
                throw new IllegalArgumentException("Category with slug already exists: " + input.getSlug());
            }
            category.setSlug(input.getSlug());
        }
        if (input.getDescription() != null) category.setDescription(input.getDescription());
        if (input.getParentId() != null) {
            Category parent = getCategoryById(input.getParentId());
            category.setParent(parent);
        }

        Category updatedCategory = categoryRepository.save(category);
        log.info("Updated category with id: {}", updatedCategory.getId());
        return updatedCategory;
    }

    @Transactional
    public boolean deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
        log.info("Deleted category with id: {}", id);
        return true;
    }
}
