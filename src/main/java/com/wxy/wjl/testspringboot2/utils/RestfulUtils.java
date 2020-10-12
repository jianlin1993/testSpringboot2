package com.wxy.wjl.testspringboot2.utils;

import org.springframework.web.client.RestTemplate;

/**
 * 通过RestTemplete发送http请求
 * 此Utils主要操作eureka
 */
public class RestfulUtils {


    /**
     * 获取eureka上注册的app 信息
     * @param eurekaUrl
     * @return
     */
    public static String getEurekaApps(String eurekaUrl){
        RestTemplate restTemplate = new RestTemplate();
        String appData = restTemplate.getForObject(eurekaUrl,  String.class);
        return appData;
    }

    /**
     * 从eureka上删除注册的服务，
     * Eureka客户端每隔一段时间（默认30秒）会发送一次心跳到注册中心续约。
     * 如果通过这种方式下线了一个服务，而没有及时停掉的话，该服务很快又会回到服务列表中。
     * @param eurekaAppUrl http://ip:port/eureka/apps/服务名(appName)/注册名(instanceId)
     */
    public static void deleteEurekaApp(String eurekaAppUrl){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(eurekaAppUrl);
    }


    /**
     * 手动将eureka上的服务设置成OUT_OF_SERVICE，此实例会处理完当前正在处理的请求，之后就不再接受新的请求。
     *
     * @param eurekaAppUrl http:// ip:port/eureka/v2/apps/appID/instanceID/status?value=OUT_OF_SERVICE
     */
    public static String setAppOutOfService(String eurekaAppUrl){
        RestTemplate restTemplate = new RestTemplate();
        String result=restTemplate.getForObject(eurekaAppUrl,String.class);
        return result;
    }


    /**
     * 获取springboot服务的环境信息
     * 需要开启监控
     *         <dependency>
     *             <groupId>org.springframework.boot</groupId>
     *             <artifactId>spring-boot-starter-actuator</artifactId>
     *         </dependency>
     * @param appUrl
     * @return
     */
    public static String getAppEnv(String appUrl){
        RestTemplate restTemplate = new RestTemplate();
        String appData = restTemplate.getForObject(appUrl,  String.class);
        return appData;
    }

    public static void main(String[] args) {
        System.out.println(getEurekaApps("http://127.0.0.1:7998/eureka/apps"));
        //System.out.println(getAppEnv("http://127.0.0.1:8082/env"));
        //deleteEurekaApp("http://127.0.0.1:7998/eureka/apps/PROVIDER/192.168.8.162:8082");
        //System.out.println(setAppOutOfService("http://127.0.0.1:7998/eureka/apps/PROVIDER/192.168.8.162:8082/status?value=OUT_OF_SERVICE"));
    }


}
