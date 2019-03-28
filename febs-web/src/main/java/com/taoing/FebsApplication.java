package com.taoing;

import com.taoing.common.properties.FebsProperties;
import com.taoing.security.properties.FebsSecurityProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.social.autoconfigure.SocialWebAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication(exclude = {SocialWebAutoConfiguration.class})
@MapperScan({"com.taoing.*.dao"})
@EnableConfigurationProperties({FebsSecurityProperties.class, FebsProperties.class})
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class FebsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FebsApplication.class, args);
        LoggerFactory.getLogger(FebsApplication.class).info(
                "FEBS started up successfully at {} {}", LocalDate.now(), LocalTime.now());
    }
}
