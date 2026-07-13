package com.minishop.system.controller;

import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.Result;
import com.minishop.system.domain.SysRole;
import com.minishop.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/list")
    @OperationLog(value = "查询角色列表", module = "角色管理")
    public Result<List<SysRole>> list(SysRole role) {
        return Result.success(roleService.list(role));
    }

    @PostMapping
    @OperationLog(value = "新增角色", module = "角色管理", type = 1)
    public Result<String> add(@RequestBody SysRole role) {
        roleService.add(role);
        return Result.success("新增成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改角色", module = "角色管理", type = 2)
    public Result<String> update(@RequestBody SysRole role) {
        roleService.update(role);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/{roleId}")
    @OperationLog(value = "删除角色", module = "角色管理", type = 3)
    public Result<String> delete(@PathVariable(value = "roleId") Long roleId) {
        roleService.delete(roleId);
        return Result.success("删除成功", null);
    }
}
