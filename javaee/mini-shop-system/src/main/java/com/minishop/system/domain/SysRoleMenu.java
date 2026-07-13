package com.minishop.system.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private Long menuId;
}
