package com.taoing.security.social;

import com.taoing.security.handler.FebsAuthenticationSuccessHandler;
import com.taoing.security.properties.FebsSecurityProperties;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class FebsSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    private FebsSecurityProperties febsSecurityProperties;

    private FebsAuthenticationSuccessHandler febsAuthenticationSuccessHandler;

    public FebsSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter socialAuthenticationFilter = (SocialAuthenticationFilter) super.postProcess(object);
        socialAuthenticationFilter.setFilterProcessesUrl(filterProcessesUrl);
        socialAuthenticationFilter.setSignupUrl(febsSecurityProperties.getSocial().getSocialRedirectUrl());
        socialAuthenticationFilter.setAuthenticationSuccessHandler(febsAuthenticationSuccessHandler);
        return (T) socialAuthenticationFilter;
    }

    public FebsSecurityProperties getFebsSecurityProperties() {
        return febsSecurityProperties;
    }

    public void setFebsSecurityProperties(FebsSecurityProperties febsSecurityProperties) {
        this.febsSecurityProperties = febsSecurityProperties;
    }

    public FebsAuthenticationSuccessHandler getFebsAuthenticationSuccessHandler() {
        return febsAuthenticationSuccessHandler;
    }

    public void setFebsAuthenticationSuccessHandler(FebsAuthenticationSuccessHandler febsAuthenticationSuccessHandler) {
        this.febsAuthenticationSuccessHandler = febsAuthenticationSuccessHandler;
    }
}
