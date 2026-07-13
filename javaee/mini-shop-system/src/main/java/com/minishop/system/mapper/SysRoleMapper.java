package com.minishop.system.mapper;

import com.minishop.system.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    List<SysRole> selectList(@Param("role") SysRole role);

    SysRole selectById(@Param("roleId") Long roleId);

    int insert(SysRole role);

    int update(SysRole role);

    int deleteById(@Param("roleId") Long roleId);
}
