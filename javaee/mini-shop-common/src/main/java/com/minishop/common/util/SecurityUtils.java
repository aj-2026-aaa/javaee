package com.minishop.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Object getPrincipal() {
        Authentication authentication = getAuthentication();
        return authentication != null ? authentication.getPrincipal() : null;
    }

    public static String getUsername() {
        Object principal = getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    public static Long getUserId() {
        Object principal = getPrincipal();
        if (principal instanceof CurrentUser) {
            return ((CurrentUser) principal).getUserId();
        }
        return null;
    }
}
