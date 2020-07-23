/*
package com.wxy.wjl.testspringboot2.config;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class SpringCacheConfig {

    public static final String CACHE_NAME = "cache1";
    @Value("30")
    private int expireTime = 30;
    */
/**
     * spring缓存配置，使用guava
     * @return
     *//*

    @Bean
    public CacheManager cacheManager(){
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(CacheBuilder.newBuilder().expireAfterWrite(expireTime, TimeUnit.SECONDS));
        List list = new ArrayList();
        list.add(CACHE_NAME);
        cacheManager.setCacheNames(list);
        return cacheManager;
    }
}
*/
