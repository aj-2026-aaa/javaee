package com.minishop.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minishop.order.domain.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;
    private String orderNo;
    private Long userId;
    private String username;
    private BigDecimal totalAmount;
    private Integer status;
    private Integer payType;
    private String address;
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    // 一对多关联：订单明细
    private List<OrderItem> items;
}
