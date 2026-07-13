package com.minishop.product.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.PageResult;
import com.minishop.common.result.Result;
import com.minishop.product.domain.Product;
import com.minishop.product.service.ProductService;
import com.minishop.product.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    @OperationLog(value = "查询商品列表", module = "商品管理")
    public Result<PageResult<ProductVO>> list(Product product,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductVO> list = productService.list(product);
        PageInfo<ProductVO> pageInfo = new PageInfo<>(list);
        PageResult<ProductVO> result = new PageResult<>(pageInfo.getTotal(), pageInfo.getList(),
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/{productId}")
    @OperationLog(value = "查询商品详情", module = "商品管理")
    public Result<ProductVO> getById(@PathVariable(value = "productId") Long productId) {
        return Result.success(productService.getById(productId));
    }

    @PostMapping
    @OperationLog(value = "新增商品", module = "商品管理", type = 1)
    public Result<String> add(@RequestBody Product product) {
        productService.add(product);
        return Result.success("新增成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改商品", module = "商品管理", type = 2)
    public Result<String> update(@RequestBody Product product) {
        productService.update(product);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/{productId}")
    @OperationLog(value = "删除商品", module = "商品管理", type = 3)
    public Result<String> delete(@PathVariable(value = "productId") Long productId) {
        productService.delete(productId);
        return Result.success("删除成功", null);
    }
}
