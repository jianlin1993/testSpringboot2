package com.wxy.wjl.testspringboot2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import java.util.Map;
import java.util.TreeMap;

/**
 * msl系统名与数据库用户名映射
 */
@Configuration
@ConfigurationProperties(prefix="msl.system.ds")
@PropertySource("classpath:application.properties")
public class MslSystemDsMap {

    private Map<String, String> map = new TreeMap<>();

    public Map<String, String> getMap() {
        return map;
    }
    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
