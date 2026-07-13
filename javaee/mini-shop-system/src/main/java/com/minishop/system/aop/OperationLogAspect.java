package com.minishop.system.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minishop.common.aop.annotation.OperationLog;
import com.minishop.system.domain.SysOperLog;
import com.minishop.system.service.OperLogService;
import com.minishop.common.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperLogService operLogService;
    private final ObjectMapper objectMapper;

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();

    @Before("@annotation(operationLog)")
    public void doBefore(OperationLog operationLog) {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    @AfterReturning(pointcut = "@annotation(operationLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, OperationLog operationLog, Object jsonResult) {
        handleLog(joinPoint, operationLog, jsonResult, null);
    }

    @AfterThrowing(pointcut = "@annotation(operationLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperationLog operationLog, Exception e) {
        handleLog(joinPoint, operationLog, null, e);
    }

    private void handleLog(JoinPoint joinPoint, OperationLog operationLog, Object jsonResult, Exception e) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();

            SysOperLog operLog = new SysOperLog();
            String title = operationLog.module().isEmpty() ? operationLog.value()
                    : operationLog.module() + " - " + operationLog.value();
            operLog.setTitle(title);
            operLog.setOperType(operationLog.type());
            operLog.setMethod(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
            operLog.setRequestMethod(request.getMethod());
            operLog.setOperName(SecurityUtils.getUsername());
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperIp(getIpAddr(request));
            operLog.setOperParam(getRequestParams(joinPoint));
            operLog.setJsonResult(jsonResult != null ? objectMapper.writeValueAsString(jsonResult) : "");
            operLog.setStatus(e == null ? 0 : 1);
            operLog.setErrorMsg(e != null ? e.getMessage() : "");
            operLog.setCostTime(System.currentTimeMillis() - TIME_THREAD_LOCAL.get());
            operLog.setCreateTime(LocalDateTime.now());

            operLogService.save(operLog);
        } catch (Exception ex) {
            log.error("操作日志记录异常", ex);
        } finally {
            TIME_THREAD_LOCAL.remove();
        }
    }

    private String getRequestParams(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) {
                return "";
            }
            Object arg = args[0];
            if (arg instanceof HttpServletRequest) {
                return "";
            }
            return objectMapper.writeValueAsString(arg);
        } catch (Exception e) {
            return "";
        }
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
