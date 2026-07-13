package com.minishop.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CreateOrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;
    private String remark;
    private List<OrderItemVO> items;

    @Data
    public static class OrderItemVO implements Serializable {
        private Long productId;
        private Integer quantity;
    }
}
