package com.minishop.product.mapper;

import com.minishop.product.domain.ProductCategory;
import com.minishop.product.vo.CategoryWithProductsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {

    ProductCategory selectById(@Param("categoryId") Long categoryId);

    List<ProductCategory> selectList(@Param("category") ProductCategory category);

    CategoryWithProductsVO selectCategoryWithProducts(@Param("categoryId") Long categoryId);

    int insert(ProductCategory category);

    int update(ProductCategory category);

    int deleteById(@Param("categoryId") Long categoryId);
}
