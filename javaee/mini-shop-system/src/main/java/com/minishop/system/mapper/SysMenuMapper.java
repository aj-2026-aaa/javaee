package com.minishop.system.mapper;

import com.minishop.system.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    List<String> selectPermsByUserId(@Param("userId") Long userId);

    List<SysMenu> selectList(@Param("menu") SysMenu menu);

    int insert(SysMenu menu);

    int update(SysMenu menu);

    int deleteById(@Param("menuId") Long menuId);
}
