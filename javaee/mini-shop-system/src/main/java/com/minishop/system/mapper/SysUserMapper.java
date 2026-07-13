package com.minishop.system.mapper;

import com.minishop.system.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    SysUser selectByUsername(@Param("username") String username);

    SysUser selectById(@Param("userId") Long userId);

    List<SysUser> selectList(@Param("user") SysUser user);

    int insert(SysUser user);

    int update(SysUser user);

    int deleteById(@Param("userId") Long userId);

    Long countNormalUsers();
}
