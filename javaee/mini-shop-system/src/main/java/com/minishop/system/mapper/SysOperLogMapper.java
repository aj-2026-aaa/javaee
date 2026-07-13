package com.minishop.system.mapper;

import com.minishop.system.domain.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysOperLogMapper {

    int insert(SysOperLog operLog);

    List<SysOperLog> selectList(@Param("operLog") SysOperLog operLog);

    int deleteById(@Param("operId") Long operId);

    Long countAll();
}
