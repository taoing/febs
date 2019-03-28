package com.taoing.web.service;

import com.taoing.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class InitService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Value("${spring.session.redis.namespace}")
    private String redisSessionNamespace;

    /**
     * bean的依赖注入完成之后执行, 进一步初始化
     */
    @PostConstruct
    public void init() {
        Set<String> keys = redisService.getKeys(redisSessionNamespace + "*");
        keys.forEach(key -> redisService.del(key));
        log.info("clean up spring session in redis");
    }
}
