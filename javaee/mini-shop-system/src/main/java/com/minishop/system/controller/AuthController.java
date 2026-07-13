package com.minishop.system.controller;

import com.minishop.common.dto.LoginDTO;
import com.minishop.common.result.Result;
import com.minishop.common.vo.UserVO;
import com.minishop.system.domain.LoginUser;
import com.minishop.common.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return Result.success("登录成功", null);
        } catch (BadCredentialsException e) {
            return Result.error("用户名或密码错误");
        }
    }

    @GetMapping("/info")
    public Result<UserVO> info() {
        Object principal = SecurityUtils.getPrincipal();
        if (!(principal instanceof LoginUser)) {
            return Result.error("未登录");
        }
        LoginUser loginUser = (LoginUser) principal;
        UserVO vo = new UserVO();
        vo.setUserId(loginUser.getUser().getUserId());
        vo.setUsername(loginUser.getUsername());
        vo.setNickname(loginUser.getUser().getNickname());
        vo.setAvatar(loginUser.getUser().getAvatar());
        vo.setStatus(loginUser.getUser().getStatus());
        vo.setRoles(loginUser.getRoles());
        vo.setPermissions(loginUser.getPermissions());
        return Result.success(vo);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Result.success("退出成功", null);
    }
}
