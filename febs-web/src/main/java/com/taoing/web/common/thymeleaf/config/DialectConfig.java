package com.taoing.web.common.thymeleaf.config;

import com.taoing.web.common.thymeleaf.dict.DictDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DialectConfig {

    @Bean
    public DictDialect getDictDialect() {
        return new DictDialect();
    }
}
