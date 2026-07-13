package com.minishop.system.service;

import com.minishop.common.exception.BusinessException;
import com.minishop.system.domain.SysUser;
import com.minishop.system.mapper.SysUserMapper;
import com.minishop.common.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public SysUser getById(Long userId) {
        return userMapper.selectById(userId);
    }

    public List<SysUser> list(SysUser user) {
        return userMapper.selectList(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysUser user) {
        SysUser exist = userMapper.selectByUsername(user.getUsername());
        if (exist != null) {
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDelFlag(0);
        user.setCreateTime(LocalDateTime.now());
        user.setCreateBy(SecurityUtils.getUserId());
        userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser user) {
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdateBy(SecurityUtils.getUserId());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.update(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long userId) {
        userMapper.deleteById(userId);
    }
}
