package com.minishop.order.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.PageResult;
import com.minishop.common.result.Result;
import com.minishop.order.domain.ShopOrder;
import com.minishop.order.service.ShopOrderService;
import com.minishop.order.vo.CreateOrderVO;
import com.minishop.order.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class ShopOrderController {

    private final ShopOrderService orderService;

    @GetMapping("/list")
    @OperationLog(value = "查询订单列表", module = "订单管理")
    public Result<PageResult<OrderVO>> list(ShopOrder order,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderVO> list = orderService.list(order);
        PageInfo<OrderVO> pageInfo = new PageInfo<>(list);
        PageResult<OrderVO> result = new PageResult<>(pageInfo.getTotal(), pageInfo.getList(),
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/{orderId}")
    @OperationLog(value = "查询订单详情", module = "订单管理")
    public Result<OrderVO> getById(@PathVariable(value = "orderId") Long orderId) {
        return Result.success(orderService.getById(orderId));
    }

    @PostMapping
    @OperationLog(value = "创建订单", module = "订单管理", type = 1)
    public Result<String> create(@RequestBody CreateOrderVO createOrderVO) {
        orderService.create(createOrderVO);
        return Result.success("创建成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改订单", module = "订单管理", type = 2)
    public Result<String> update(@RequestBody ShopOrder order) {
        orderService.update(order);
        return Result.success("修改成功", null);
    }

    @PutMapping("/{orderId}/pay")
    @OperationLog(value = "订单支付", module = "订单管理", type = 2)
    public Result<String> pay(@PathVariable(value = "orderId") Long orderId) {
        orderService.pay(orderId);
        return Result.success("支付成功", null);
    }

    @DeleteMapping("/{orderId}")
    @OperationLog(value = "删除订单", module = "订单管理", type = 3)
    public Result<String> delete(@PathVariable(value = "orderId") Long orderId) {
        orderService.delete(orderId);
        return Result.success("删除成功", null);
    }
}
