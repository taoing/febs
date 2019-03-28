package com.taoing.security.session;

import com.taoing.security.properties.FebsSecurityProperties;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理会话失效
 */
public class FebsInvalidSessionStrategy implements InvalidSessionStrategy {

    private FebsSecurityProperties securityProperties;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
        redirectStrategy.sendRedirect(request, response, securityProperties.getLogoutUrl());
    }

    public FebsSecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(FebsSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
