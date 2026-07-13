package com.minishop.product.service;

import com.minishop.product.domain.Product;
import com.minishop.product.mapper.ProductMapper;
import com.minishop.product.vo.ProductVO;
import com.minishop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public ProductVO getById(Long productId) {
        return productMapper.selectById(productId);
    }

    public List<ProductVO> list(Product product) {
        return productMapper.selectList(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Product product) {
        product.setDelFlag(0);
        product.setCreateTime(LocalDateTime.now());
        product.setCreateBy(SecurityUtils.getUserId());
        productMapper.insert(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Product product) {
        product.setUpdateTime(LocalDateTime.now());
        product.setUpdateBy(SecurityUtils.getUserId());
        productMapper.update(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long productId) {
        productMapper.deleteById(productId);
    }
}
