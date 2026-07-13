package com.minishop.system.service.impl;

import com.minishop.common.exception.BusinessException;
import com.minishop.system.domain.LoginUser;
import com.minishop.system.domain.SysRole;
import com.minishop.system.domain.SysUser;
import com.minishop.system.mapper.SysMenuMapper;
import com.minishop.system.mapper.SysRoleMapper;
import com.minishop.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper userMapper;
    private final SysRoleMapper roleMapper;
    private final SysMenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        if (user.getStatus() != 0) {
            throw new BusinessException("用户已被禁用");
        }
        List<SysRole> roles = roleMapper.selectRolesByUserId(user.getUserId());
        List<String> roleKeys = roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList());
        List<String> perms = menuMapper.selectPermsByUserId(user.getUserId());
        return new LoginUser(user, roleKeys, perms);
    }
}
