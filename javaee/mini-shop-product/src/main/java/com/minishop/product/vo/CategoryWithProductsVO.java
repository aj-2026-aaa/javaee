package com.minishop.product.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryWithProductsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private Integer sort;
    private Integer status;

    // 一对多关联：分类下的商品列表
    private List<ProductVO> products;
}
