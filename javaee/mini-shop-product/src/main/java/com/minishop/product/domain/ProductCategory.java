package com.minishop.product.domain;

import com.minishop.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long categoryId;
    private String categoryName;
    private Long parentId;
    private Integer sort;
    private Integer status;
}
