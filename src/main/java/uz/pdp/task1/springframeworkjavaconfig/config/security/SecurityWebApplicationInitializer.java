package uz.pdp.task1.springframeworkjavaconfig.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    @Override
    protected boolean enableHttpSessionEventPublisher() {  // bu security filter chain proxy
        return true;
    }
}
