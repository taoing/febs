package com.taoing.security.config;

import com.taoing.security.handler.FebsAuthenticationSuccessHandler;
import com.taoing.security.properties.FebsSecurityProperties;
import com.taoing.security.social.FebsSpringSocialConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * spring social配置中心
 */
@Configuration
@EnableSocial
@Order(1)
public class FebsSocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FebsAuthenticationSuccessHandler febsAuthenticationSuccessHandler;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        jdbcUsersConnectionRepository.setTablePrefix("t_");
        return jdbcUsersConnectionRepository;
    }

    @Bean
    public SpringSocialConfigurer febsSocialSecurityConfig(FebsSecurityProperties securityProperties) {
        FebsSpringSocialConfigurer febsSpringSocialConfigurer = new FebsSpringSocialConfigurer(
                securityProperties.getSocial().getFilterProcessesUrl());
        febsSpringSocialConfigurer.setFebsSecurityProperties(securityProperties);
        febsSpringSocialConfigurer.setFebsAuthenticationSuccessHandler(febsAuthenticationSuccessHandler);
        return febsSpringSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
