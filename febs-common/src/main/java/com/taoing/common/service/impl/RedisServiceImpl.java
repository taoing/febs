package com.taoing.common.service.impl;

import com.taoing.common.domain.RedisInfo;
import com.taoing.common.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.function.Function;

/**
 * redis工具类, 封装了几个常用的redis命令,
 * 可根据实际需要按类似的方式扩展
 */
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JedisPool jedisPool;

    /**
     * 处理jedis请求
     *
     * @param f 处理逻辑, 通过lambda行为参数化
     * @return 处理结果
     */
    private Object executeByJedis(Function<Jedis, Object> f) {
        try (Jedis jedis = jedisPool.getResource()) {
            // 调用函数
            return f.apply(jedis);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<RedisInfo> getRedisInfo() {
        String info = (String) this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        List<RedisInfo> infoList = new ArrayList<>();
        String[] strs = Objects.requireNonNull(info).split("\n");
        RedisInfo redisInfo;
        if (strs.length > 0) {
            for (String str1 : strs) {
                redisInfo = new RedisInfo();
                String[] str = str1.split(":");
                if (str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    redisInfo.setKey(key);
                    redisInfo.setValue(value);
                    infoList.add(redisInfo);
                }
            }
        }
        return infoList;
    }

    @Override
    public Map<String, Object> getKeysSize() {
        long dbSize = (long) this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.dbSize();
                    return client.getIntegerReply();
                }
        );
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() {
        String info = (String) this.executeByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        String[] strs = Objects.requireNonNull(info).split("\n");
        Map<String, Object> map = null;
        for (String s : strs) {
            String[] detail = s.split(":");
            if ("used_memory".equals(detail[0])) {
                map = new HashMap<>();
                map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", System.currentTimeMillis());
                break;
            }
        }
        return map;
    }

    @Override
    public Set<String> getKeys(String pattern) {
        return (Set<String>) this.executeByJedis(j -> j.keys(pattern));
    }

    @Override
    public String get(String key) {
        return (String) this.executeByJedis(j -> j.get(key));
    }

    @Override
    public String set(String key, String value) {
        return (String) this.executeByJedis(j -> j.set(key, value));
    }

    @Override
    public Long del(String... key) {
        return (Long) this.executeByJedis(j -> j.del(key));
    }

    @Override
    public Boolean exists(String key) {
        return (Boolean) this.executeByJedis(j -> j.exists(key));
    }

    @Override
    public Long pttl(String key) {
        return (Long) this.executeByJedis(j -> j.pttl(key));
    }

    @Override
    public Long pexpire(String key, Long milliseconds) {
        return (Long) this.executeByJedis(j -> j.pexpire(key, milliseconds));
    }
}
