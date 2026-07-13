package com.minishop.order.service;

import com.minishop.common.exception.BusinessException;
import com.minishop.order.domain.OrderItem;
import com.minishop.order.domain.ShopOrder;
import com.minishop.order.mapper.OrderItemMapper;
import com.minishop.order.mapper.ShopOrderMapper;
import com.minishop.order.vo.CreateOrderVO;
import com.minishop.order.vo.OrderVO;
import com.minishop.product.service.ProductService;
import com.minishop.product.vo.ProductVO;
import com.minishop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopOrderService {

    private final ShopOrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductService productService;

    public OrderVO getById(Long orderId) {
        return orderMapper.selectById(orderId);
    }

    public List<OrderVO> list(ShopOrder order) {
        return orderMapper.selectList(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void create(CreateOrderVO createOrderVO) {
        if (createOrderVO.getItems() == null || createOrderVO.getItems().isEmpty()) {
            throw new BusinessException("订单商品不能为空");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderVO.OrderItemVO itemVO : createOrderVO.getItems()) {
            if (itemVO.getQuantity() == null || itemVO.getQuantity() <= 0) {
                throw new BusinessException("商品数量必须大于0");
            }
            ProductVO product = productService.getById(itemVO.getProductId());
            if (product == null) {
                throw new BusinessException("商品不存在");
            }
            if (product.getStock() < itemVO.getQuantity()) {
                throw new BusinessException("商品库存不足：" + product.getProductName());
            }

            OrderItem item = new OrderItem();
            item.setProductId(product.getProductId());
            item.setProductName(product.getProductName());
            item.setProductImage(product.getImage());
            item.setPrice(product.getPrice());
            item.setQuantity(itemVO.getQuantity());
            item.setTotalAmount(product.getPrice().multiply(new BigDecimal(itemVO.getQuantity())));
            orderItems.add(item);
            totalAmount = totalAmount.add(item.getTotalAmount());
        }

        ShopOrder order = new ShopOrder();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
        order.setUserId(SecurityUtils.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus(0);
        order.setPayType(1);
        order.setAddress(createOrderVO.getAddress());
        order.setRemark(createOrderVO.getRemark());
        order.setCreateTime(LocalDateTime.now());
        orderMapper.insert(order);

        for (OrderItem item : orderItems) {
            item.setOrderId(order.getOrderId());
        }
        orderItemMapper.batchInsert(orderItems);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ShopOrder order) {
        orderMapper.update(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void pay(Long orderId) {
        ShopOrder order = new ShopOrder();
        order.setOrderId(orderId);
        order.setStatus(1);
        order.setPayTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long orderId) {
        orderItemMapper.deleteByOrderId(orderId);
        orderMapper.deleteById(orderId);
    }
}
