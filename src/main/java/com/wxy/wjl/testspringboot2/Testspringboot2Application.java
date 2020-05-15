package com.wxy.wjl.testspringboot2;

import com.wxy.wjl.testspringboot2.Controller.AddCO;
import com.wxy.wjl.testspringboot2.Controller.SocketServer;
import com.wxy.wjl.testspringboot2.domain.Bill;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class Testspringboot2Application {

    public static void main(String[] args) {

        ApplicationContext applicationContext= SpringApplication.run(Testspringboot2Application.class, args);
        applicationContext.getBean(SocketServer.class).start(8088);
    }

}

