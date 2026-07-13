package com.minishop.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productId;
    private Long categoryId;
    private String productName;
    private BigDecimal price;
    private Integer stock;
    private String image;
    private String description;
    private Integer status;

    // 一对一关联：商品所属分类
    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
