package com.minishop.system.domain;

import com.minishop.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private Integer status;
}
