package com.minishop.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minishop.common.util.CurrentUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoginUser implements UserDetails, CurrentUser {

    private static final long serialVersionUID = 1L;

    private SysUser user;
    private List<String> roles;
    private List<String> permissions;

    public LoginUser() {}

    public LoginUser(SysUser user, List<String> roles, List<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roleAuthorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
        List<SimpleGrantedAuthority> permAuthorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        roleAuthorities.addAll(permAuthorities);
        return roleAuthorities;
    }

    @Override
    public String getPassword() {
        return user != null ? user.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return user != null ? user.getUsername() : null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return user != null && user.getStatus() != null && user.getStatus() == 0;
    }

    @Override
    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }
}
