package com.minishop.system.service;

import com.minishop.system.domain.SysRole;
import com.minishop.system.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final SysRoleMapper roleMapper;

    public List<SysRole> list(SysRole role) {
        return roleMapper.selectList(role);
    }

    public SysRole getById(Long roleId) {
        return roleMapper.selectById(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysRole role) {
        role.setCreateTime(LocalDateTime.now());
        roleMapper.insert(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole role) {
        roleMapper.update(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId) {
        roleMapper.deleteById(roleId);
    }
}
