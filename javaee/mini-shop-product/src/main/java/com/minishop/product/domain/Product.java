package com.minishop.product.domain;

import com.minishop.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private Long categoryId;
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private String image;
    private String description;
    private Integer status;
}
