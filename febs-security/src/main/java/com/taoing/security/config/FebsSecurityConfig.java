package com.taoing.security.config;

import com.taoing.common.domain.FebsConstant;
import com.taoing.security.code.ValidateCodeGenerator;
import com.taoing.security.code.img.ImageCodeFilter;
import com.taoing.security.code.img.ImageCodeGenerator;
import com.taoing.security.code.sms.DefaultSmsSender;
import com.taoing.security.code.sms.SmsCodeFilter;
import com.taoing.security.code.sms.SmsCodeSender;
import com.taoing.security.handler.FebsAuthenticationAccessDeniedHandler;
import com.taoing.security.handler.FebsAuthenticationFailureHandler;
import com.taoing.security.handler.FebsAuthenticationSuccessHandler;
import com.taoing.security.handler.FebsLogoutHandler;
import com.taoing.security.properties.FebsSecurityProperties;
import com.taoing.security.service.FebsUserDetailService;
import com.taoing.security.session.FebsExpiredSessionStrategy;
import com.taoing.security.session.FebsInvalidSessionStrategy;
import com.taoing.security.xss.XssFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * security 配置中心
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FebsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private FebsAuthenticationSuccessHandler febsAuthenticationSuccessHandler;

    @Autowired
    private FebsAuthenticationFailureHandler febsAuthenticationFailureHandler;

    @Autowired
    private FebsSecurityProperties febsSecurityProperties;

    @Autowired
    private FebsSmsCodeAuthenticationSecurityConfig febsSmsCodeAuthenticationSecurityConfig;

    @Autowired
    private FebsUserDetailService febsUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringSocialConfigurer febsSocialSecurityConfig;

    // spring security自带的密码加密工具类
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 处理rememberMe自动登录认证
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] anonResourcesUrl = StringUtils.splitByWholeSeparatorPreserveAllTokens(
            febsSecurityProperties.getAnonResourcesUrl(), ",");

        ImageCodeFilter imageCodeFilter = new ImageCodeFilter();
        imageCodeFilter.setAuthenticationFailureHandler(febsAuthenticationFailureHandler);
        imageCodeFilter.setSecurityProperties(febsSecurityProperties);
        imageCodeFilter.afterPropertiesSet();

        SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
        smsCodeFilter.setAuthenticationFailureHandler(febsAuthenticationFailureHandler);
        smsCodeFilter.setSecurityProperties(febsSecurityProperties);
        smsCodeFilter.setSessionRegistry(sessionRegistry());
        smsCodeFilter.afterPropertiesSet();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()) // 权限不足处理器
            .and()
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class) // 短信验证码校验
                .addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class) // 图形验证码校验过滤器
                .formLogin()  // 表单方式
                .loginPage(febsSecurityProperties.getLoginUrl()) // 未认证跳转url
                .loginProcessingUrl(febsSecurityProperties.getCode().getImage().getLoginProcessingUrl()) // 处理登录认证url
                .successHandler(febsAuthenticationSuccessHandler) // 处理登录成功
                .failureHandler(febsAuthenticationFailureHandler) // 处理登录失败
            .and()
                .rememberMe() // 添加记住我功能
                .tokenRepository(persistentTokenRepository()) // 配置token持久化仓库
                .tokenValiditySeconds(febsSecurityProperties.getRememberMeTimeout()) // rememberMe过期时间, 单位秒
                .userDetailsService(febsUserDetailsService) // 处理自动登录逻辑
            .and()
                .sessionManagement() // 配置session管理器
                .invalidSessionStrategy(invalidSessionStrategy()) // 处理session失效
                .maximumSessions(febsSecurityProperties.getSession().getMaximumSessions()) // 最大并发登录数量
                .expiredSessionStrategy(new FebsExpiredSessionStrategy()) // 处理并发登录被踢出
                .sessionRegistry(sessionRegistry()) // 配置session注册中心
            .and().and()
                .logout() // 配置登出
                .addLogoutHandler(logoutHandler()) // 配置登出处理器
                .logoutUrl(febsSecurityProperties.getLogoutUrl()) // 处理登出url
                .logoutSuccessUrl("/") // 登出成功后跳转到 /
                .deleteCookies("JSESSIONID") // 删除JESSIONID
            .and()
                .authorizeRequests() // 授权配置
                .antMatchers(anonResourcesUrl).permitAll() // 免认证静态资源路径
                .antMatchers(
                        febsSecurityProperties.getLoginUrl(),
                        FebsConstant.FEBS_REGIST_URL,
                        febsSecurityProperties.getCode().getImage().getCreateUrl(),
                        febsSecurityProperties.getCode().getSms().getCreateUrl(),
                        febsSecurityProperties.getSocial().getSocialRedirectUrl(),
                        febsSecurityProperties.getSocial().getSocialBindUrl(),
                        febsSecurityProperties.getSocial().getSocialRegisterUrl()
                ).permitAll() // 配置免认证路径
                .anyRequest() // 其他所有请求
                .authenticated() // 都需要认证
            .and()
                .csrf().disable()
                .apply(febsSmsCodeAuthenticationSecurityConfig) // 添加短信认证码认证流程
            .and()
                .apply(febsSocialSecurityConfig); // social配置
    }

    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator")
    public ValidateCodeGenerator imageCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(febsSecurityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsSender();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public FebsAuthenticationSuccessHandler febsAuthenticationSuccessHandler() {
        FebsAuthenticationSuccessHandler authenticationSuccessHandler =
                new FebsAuthenticationSuccessHandler();
        authenticationSuccessHandler.setSessionRegistry(sessionRegistry());
        return authenticationSuccessHandler;
    }

    @Bean
    public LogoutHandler logoutHandler() {
        FebsLogoutHandler febsLogoutHandler = new FebsLogoutHandler();
        febsLogoutHandler.setSessionRegistry(sessionRegistry());
        return febsLogoutHandler;
    }

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        FebsInvalidSessionStrategy febsInvalidSessionStrategy = new FebsInvalidSessionStrategy();
        febsInvalidSessionStrategy.setSecurityProperties(febsSecurityProperties);
        return febsInvalidSessionStrategy;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new FebsAuthenticationAccessDeniedHandler();
    }

    /**
     * XssFilter Bean
     */
    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
        initParameters.put("isIncludeRichText", "true");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
