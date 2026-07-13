package com.minishop.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.common.result.PageResult;
import com.minishop.common.result.Result;
import com.minishop.system.domain.SysUser;
import com.minishop.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    @OperationLog(value = "查询用户列表", module = "用户管理")
    public Result<PageResult<SysUser>> list(SysUser user,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = userService.list(user);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        PageResult<SysUser> result = new PageResult<>(pageInfo.getTotal(), pageInfo.getList(),
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
        return Result.success(result);
    }

    @GetMapping("/{userId}")
    @OperationLog(value = "查询用户详情", module = "用户管理")
    public Result<SysUser> getById(@PathVariable(value = "userId") Long userId) {
        return Result.success(userService.getById(userId));
    }

    @PostMapping
    @OperationLog(value = "新增用户", module = "用户管理", type = 1)
    public Result<String> add(@RequestBody SysUser user) {
        userService.add(user);
        return Result.success("新增成功", null);
    }

    @PutMapping
    @OperationLog(value = "修改用户", module = "用户管理", type = 2)
    public Result<String> update(@RequestBody SysUser user) {
        userService.update(user);
        return Result.success("修改成功", null);
    }

    @DeleteMapping("/{userId}")
    @OperationLog(value = "删除用户", module = "用户管理", type = 3)
    public Result<String> delete(@PathVariable(value = "userId") Long userId) {
        userService.delete(userId);
        return Result.success("删除成功", null);
    }
}
