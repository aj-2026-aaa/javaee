package com.minishop.system.config;

import com.minishop.system.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/api/auth/login", "/api/auth/login-form", "/error").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .requestMatchers("/api/auth/info", "/api/auth/logout").authenticated()
                .requestMatchers("/api/dashboard/**").authenticated()
                .requestMatchers("/api/user/**").hasRole("admin")
                .requestMatchers("/api/role/**", "/api/menu/**").hasRole("admin")
                .requestMatchers("/api/log/**").hasAuthority("system:log:list")
                .requestMatchers("/api/product/**").hasAuthority("product:manage")
                .requestMatchers("/api/category/**").hasAuthority("product:manage")
                .requestMatchers("/api/order/**").hasAuthority("order:manage")
                .requestMatchers("/dashboard", "/", "/product/list", "/order/list", "/system/user", "/system/log").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/api/auth/login-form")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    String accept = request.getHeader("Accept");
                    String ajax = request.getHeader("X-Requested-With");
                    if ("XMLHttpRequest".equals(ajax) || (accept != null && accept.contains("application/json"))) {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(401);
                        response.getWriter().write("{\"code\":401,\"message\":\"未登录或登录已过期\"}");
                    } else {
                        response.sendRedirect("/login");
                    }
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    String accept = request.getHeader("Accept");
                    String ajax = request.getHeader("X-Requested-With");
                    if ("XMLHttpRequest".equals(ajax) || (accept != null && accept.contains("application/json"))) {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(403);
                        response.getWriter().write("{\"code\":403,\"message\":\"没有访问权限\"}");
                    } else {
                        response.sendRedirect("/login");
                    }
                })
            );
        return http.build();
    }
}
