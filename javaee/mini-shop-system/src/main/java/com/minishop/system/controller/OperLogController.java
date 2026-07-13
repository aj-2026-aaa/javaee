package com.minishop.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minishop.common.result.PageResult;
import com.minishop.common.result.Result;
import com.minishop.system.domain.SysOperLog;
import com.minishop.system.service.OperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/log")
@RequiredArgsConstructor
public class OperLogController {

    private final OperLogService operLogService;

    @GetMapping("/list")
    public Result<PageResult<SysOperLog>> list(SysOperLog operLog,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysOperLog> list = operLogService.list(operLog);
        PageInfo<SysOperLog> pageInfo = new PageInfo<>(list);
        PageResult<SysOperLog> result = new PageResult<>(pageInfo.getTotal(), pageInfo.getList(),
                pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
        return Result.success(result);
    }

    @DeleteMapping("/{operId}")
    public Result<String> delete(@PathVariable(value = "operId") Long operId) {
        operLogService.delete(operId);
        return Result.success("删除成功", null);
    }
}
