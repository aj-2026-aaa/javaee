package com.minishop.product.controller;

import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.Result;
import com.minishop.product.domain.ProductCategory;
import com.minishop.product.service.ProductCategoryService;
import com.minishop.product.vo.CategoryWithProductsVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping("/list")
    @OperationLog(value = "查询商品分类列表", module = "商品管理")
    public Result<List<ProductCategory>> list(ProductCategory category) {
        return Result.success(categoryService.list(category));
    }

    @GetMapping("/{categoryId}")
    @OperationLog(value = "查询商品分类详情", module = "商品管理")
    public Result<ProductCategory> getById(@PathVariable(value = "categoryId") Long categoryId) {
        return Result.success(categoryService.getById(categoryId));
    }

    @GetMapping("/{categoryId}/products")
    @OperationLog(value = "查询分类及商品", module = "商品管理")
    public Result<CategoryWithProductsVO> getCategoryWithProducts(@PathVariable(value = "categoryId") Long categoryId) {
        return Result.success(categoryService.getCategoryWithProducts(categoryId));
    }

    @PostMapping
    @OperationLog(value = "新增商品分类", module = "商品管理", type = 1)
    public Result<String> add(@RequestBody ProductCategory category) {
        categoryService.add(category);
        return Result.success("新增成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改商品分类", module = "商品管理", type = 2)
    public Result<String> update(@RequestBody ProductCategory category) {
        categoryService.update(category);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/{categoryId}")
    @OperationLog(value = "删除商品分类", module = "商品管理", type = 3)
    public Result<String> delete(@PathVariable(value = "categoryId") Long categoryId) {
        categoryService.delete(categoryId);
        return Result.success("删除成功", null);
    }
}
