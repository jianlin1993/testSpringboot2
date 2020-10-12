package com.wxy.wjl.testspringboot2.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class WjlKafkaProducer {
    KafkaProducer producer=null;


    /**
     * 初始化生产者
     */
    public void containerInit(){
        Properties var2 = new Properties();

        //kafka broker地址 如果是集群可配置多个，最好全部配置上，因为如果只配置一个，如果此broker挂掉，可能会出现连接失败的情况
        //在启动consumer时配置的broker地址的。不需要将cluster中所有的broker都配置上，因为启动后会自动的发现cluster所有的broker。
        //    它配置的格式是：host1:port1;host2:port2…
        var2.put("bootstrap.servers", "");

        //client.id 发出请求时传递给服务器的id字符串。这样做的目的是通过允许将逻辑应用程序名称包含在服务器端请求日志中，从而能够跟踪ip / port之外的请求源，如果不手动指定，代码中会自动生成一个id。
        var2.put("client.id", "");
        var2.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        var2.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        var2.put("linger.ms", "5");
        var2.put("request.timeout.ms", "2000");
        var2.put("retries", 1);
        var2.put("max.block.ms", "60000");
        producer = new KafkaProducer(var2);


        //interceptor.classes 用作拦截器的类的列表。通过实现ProducerInterceptor接口，您可以在生产者发布到Kafka集群之前拦截（并可能会改变）生产者收到的记录。默认情况下，没有拦截器，可自定义拦截器。
        //partitioner.class 实现Partitioner接口的分区器类。默认使用DefaultPartitioner来进行分区。
    }

    public void send(String topic){
        producer.send(new ProducerRecord(topic, "", ""), new Callback() {
            public void onCompletion(RecordMetadata var1, Exception var2) {
                if (var2 != null) {
                    if (var1 == null) {
                        System.out.println("topic:"+topic);
                    } else {
                        System.out.println(java.lang.String.format("topic:%s,partition:%d,offset:%d,timestamp:%d", var1.topic(), var1.partition(), var1.offset(), var1.timestamp())+var2);
                    }
                }

            }
        });
    }



}
