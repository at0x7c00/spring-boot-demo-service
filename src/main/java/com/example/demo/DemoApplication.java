package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class DemoApplication {

    final Logger log = Logger.getLogger("DemoApplication");

    @Value("${book.name}")
    private String name;

    @Autowired
    private DiscoveryClient client;

    public static void main(String[] args) {
        String str = null;
        //str.split(".");
        SpringApplication.run(DemoApplication.class, args);

    }

    @RequestMapping("/hello")
    public String hello(){
        client.getServices().forEach(id ->{
            client.getInstances(id).forEach((ServiceInstance instance) ->{
                log.info("host:"+instance.getHost()+",service_id:" + instance.getServiceId());
            });
        });
        return "Hello," + name;
    }

}
