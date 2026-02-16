package com.example.springboot_graphql_product_api.repository;

import com.example.springboot_graphql_product_api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Optional<Category> findBySlug(String slug);
    
    Optional<Category> findByName(String name);
    
    List<Category> findByParentIsNull();
    
    List<Category> findByParentId(Long parentId);
    
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
    List<Category> findRootCategoriesWithChildren();
    
    boolean existsBySlug(String slug);
}
