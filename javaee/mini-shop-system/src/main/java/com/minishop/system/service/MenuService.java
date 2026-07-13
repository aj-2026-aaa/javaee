package com.minishop.system.service;

import com.minishop.system.domain.SysMenu;
import com.minishop.system.mapper.SysMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final SysMenuMapper menuMapper;

    public List<SysMenu> list(SysMenu menu) {
        return menuMapper.selectList(menu);
    }

    public List<SysMenu> getUserMenus(Long userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SysMenu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menuMapper.insert(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenu menu) {
        menuMapper.update(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long menuId) {
        menuMapper.deleteById(menuId);
    }
}
