package com.minishop.system.domain;

import com.minishop.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private Integer status;
}
