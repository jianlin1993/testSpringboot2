package com.wxy.wjl.testspringboot2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.wxy.wjl.providerapi.service", "com.wxy.wjl.testspringboot2.cloud"})
@MapperScan({"com.wxy.wjl.testspringboot2.mapper","com.wxy.wjl.testspringboot2.job.dal.dao"})
public class Testspringboot2Application {

    public static void main(String[] args) {

        ApplicationContext applicationContext= SpringApplication.run(Testspringboot2Application.class, args);
        //applicationContext.getBean(SocketServer.class).start(8089);
    }

}

