package com.minishop.system.config;

import com.minishop.system.domain.SysUser;
import com.minishop.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SysUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        resetPassword("admin", "123456", "超级管理员");
        resetPassword("test", "123456", "测试用户");
    }

    private void resetPassword(String username, String rawPassword, String nickname) {
        SysUser user = userMapper.selectByUsername(username);
        if (user == null) {
            user = new SysUser();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setNickname(nickname);
            user.setStatus(0);
            user.setDelFlag(0);
            user.setCreateTime(LocalDateTime.now());
            userMapper.insert(user);
            log.info("初始化用户: {}", username);
        } else {
            if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(rawPassword));
                user.setUpdateTime(LocalDateTime.now());
                userMapper.update(user);
                log.info("重置用户 {} 密码为 {}", username, rawPassword);
            }
        }
    }
}
