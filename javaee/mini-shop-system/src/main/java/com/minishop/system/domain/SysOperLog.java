package com.minishop.system.domain;

import com.minishop.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long operId;
    private String title;
    private Integer operType;
    private String method;
    private String requestMethod;
    private String operName;
    private String operUrl;
    private String operIp;
    private String operParam;
    private String jsonResult;
    private Integer status;
    private String errorMsg;
    private Long costTime;
}
