package com.example.springboot_graphql_product_api.service;

import com.example.springboot_graphql_product_api.dto.CreateBrandInput;
import com.example.springboot_graphql_product_api.dto.UpdateBrandInput;
import com.example.springboot_graphql_product_api.exception.BrandNotFoundException;
import com.example.springboot_graphql_product_api.model.Brand;
import com.example.springboot_graphql_product_api.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional(readOnly = true)
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
            .orElseThrow(() -> new BrandNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Brand getBrandBySlug(String slug) {
        return brandRepository.findBySlug(slug)
            .orElseThrow(() -> new BrandNotFoundException(slug));
    }

    @Transactional
    public Brand createBrand(CreateBrandInput input) {
        if (brandRepository.existsBySlug(input.getSlug())) {
            throw new IllegalArgumentException("Brand with slug already exists: " + input.getSlug());
        }

        Brand brand = new Brand();
        brand.setName(input.getName());
        brand.setSlug(input.getSlug());
        brand.setDescription(input.getDescription());
        brand.setLogoUrl(input.getLogoUrl());
        brand.setWebsite(input.getWebsite());

        Brand savedBrand = brandRepository.save(brand);
        log.info("Created brand with id: {}", savedBrand.getId());
        return savedBrand;
    }

    @Transactional
    public Brand updateBrand(Long id, UpdateBrandInput input) {
        Brand brand = getBrandById(id);

        if (input.getName() != null) brand.setName(input.getName());
        if (input.getSlug() != null) {
            if (!brand.getSlug().equals(input.getSlug()) && brandRepository.existsBySlug(input.getSlug())) {
                throw new IllegalArgumentException("Brand with slug already exists: " + input.getSlug());
            }
            brand.setSlug(input.getSlug());
        }
        if (input.getDescription() != null) brand.setDescription(input.getDescription());
        if (input.getLogoUrl() != null) brand.setLogoUrl(input.getLogoUrl());
        if (input.getWebsite() != null) brand.setWebsite(input.getWebsite());

        Brand updatedBrand = brandRepository.save(brand);
        log.info("Updated brand with id: {}", updatedBrand.getId());
        return updatedBrand;
    }

    @Transactional
    public boolean deleteBrand(Long id) {
        Brand brand = getBrandById(id);
        brandRepository.delete(brand);
        log.info("Deleted brand with id: {}", id);
        return true;
    }
}
