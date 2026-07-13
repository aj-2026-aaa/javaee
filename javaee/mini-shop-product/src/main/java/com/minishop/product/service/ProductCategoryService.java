package com.minishop.product.service;

import com.minishop.product.domain.ProductCategory;
import com.minishop.product.mapper.ProductCategoryMapper;
import com.minishop.product.vo.CategoryWithProductsVO;
import com.minishop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryMapper categoryMapper;

    public ProductCategory getById(Long categoryId) {
        return categoryMapper.selectById(categoryId);
    }

    public CategoryWithProductsVO getCategoryWithProducts(Long categoryId) {
        return categoryMapper.selectCategoryWithProducts(categoryId);
    }

    public List<ProductCategory> list(ProductCategory category) {
        return categoryMapper.selectList(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(ProductCategory category) {
        category.setDelFlag(0);
        category.setCreateTime(LocalDateTime.now());
        category.setCreateBy(SecurityUtils.getUserId());
        categoryMapper.insert(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ProductCategory category) {
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateBy(SecurityUtils.getUserId());
        categoryMapper.update(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long categoryId) {
        categoryMapper.deleteById(categoryId);
    }
}
