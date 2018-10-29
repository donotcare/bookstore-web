package ru.otus.bookstoreweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityAclConfiguration extends GlobalMethodSecurityConfiguration {
    @Autowired
    private MethodSecurityExpressionHandler expressionHandler;

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return expressionHandler;
    }
}
