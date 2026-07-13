package com.minishop.order.mapper;

import com.minishop.order.domain.ShopOrder;
import com.minishop.order.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopOrderMapper {

    OrderVO selectById(@Param("orderId") Long orderId);

    List<OrderVO> selectList(@Param("order") ShopOrder order);

    int insert(ShopOrder order);

    int update(ShopOrder order);

    int deleteById(@Param("orderId") Long orderId);

    Long countAll();
}
