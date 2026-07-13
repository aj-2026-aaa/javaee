package com.minishop.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private Integer status;
    private List<String> roles;
    private List<String> permissions;
}
