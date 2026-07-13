package com.minishop.system.service;

import com.minishop.system.domain.SysOperLog;
import com.minishop.system.mapper.SysOperLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperLogService {

    private final SysOperLogMapper operLogMapper;

    public void save(SysOperLog operLog) {
        operLogMapper.insert(operLog);
    }

    public List<SysOperLog> list(SysOperLog operLog) {
        return operLogMapper.selectList(operLog);
    }

    public void delete(Long operId) {
        operLogMapper.deleteById(operId);
    }
}
