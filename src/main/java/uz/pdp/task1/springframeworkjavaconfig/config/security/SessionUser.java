package uz.pdp.task1.springframeworkjavaconfig.config.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.pdp.task1.springframeworkjavaconfig.domains.AuthUser;

import java.util.Objects;
@Component
public class SessionUser {
    public AuthUser getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getAuthUser();
        }
        return null;
    }

    public Long getUserId() {
        if (Objects.isNull(getUser().getId())) {
            return null;
        }
        return getUser().getId();
    }
}
