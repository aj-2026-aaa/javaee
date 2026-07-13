package com.minishop.product.mapper;

import com.minishop.product.domain.Product;
import com.minishop.product.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductVO selectById(@Param("productId") Long productId);

    List<ProductVO> selectList(@Param("product") Product product);

    int insert(Product product);

    int update(Product product);

    int deleteById(@Param("productId") Long productId);

    Long countNormalProducts();
}
