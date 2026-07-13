package com.minishop.order.mapper;

import com.minishop.order.domain.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    int insert(OrderItem item);

    int batchInsert(@Param("items") List<OrderItem> items);

    List<OrderItem> selectByOrderId(@Param("orderId") Long orderId);

    int deleteByOrderId(@Param("orderId") Long orderId);
}
