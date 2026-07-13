package com.minishop.system.controller;

import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.Result;
import com.minishop.system.domain.SysMenu;
import com.minishop.system.service.MenuService;
import com.minishop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/list")
    @OperationLog(value = "查询菜单列表", module = "菜单管理")
    public Result<List<SysMenu>> list(SysMenu menu) {
        return Result.success(menuService.list(menu));
    }

    @GetMapping("/user")
    @OperationLog(value = "查询当前用户菜单", module = "菜单管理")
    public Result<List<SysMenu>> userMenus() {
        Long userId = SecurityUtils.getUserId();
        return Result.success(menuService.getUserMenus(userId));
    }

    @PostMapping
    @OperationLog(value = "新增菜单", module = "菜单管理", type = 1)
    public Result<String> add(@RequestBody SysMenu menu) {
        menuService.add(menu);
        return Result.success("新增成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改菜单", module = "菜单管理", type = 2)
    public Result<String> update(@RequestBody SysMenu menu) {
        menuService.update(menu);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/{menuId}")
    @OperationLog(value = "删除菜单", module = "菜单管理", type = 3)
    public Result<String> delete(@PathVariable(value = "menuId") Long menuId) {
        menuService.delete(menuId);
        return Result.success("删除成功", null);
    }
}
