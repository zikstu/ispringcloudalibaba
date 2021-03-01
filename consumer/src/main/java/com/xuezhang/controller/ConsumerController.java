package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/26 22:25
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;

    public ConsumerController(DiscoveryClient discoveryClient, RestTemplate restTemplate){
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/instances")
    public List<ServiceInstance> instances(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("provider");

        return instances;
    }

//    @GetMapping("/index")
//    public String index(){
//        List<ServiceInstance> list = this.discoveryClient.getInstances("provider");
//
//        int index = ThreadLocalRandom.current().nextInt(list.size());
//
//        ServiceInstance serviceInstance = list.get(index);
//
//        String url = serviceInstance.getUri() + "/provider/index";
//
//        String s = this.restTemplate.getForObject(url, String.class);
//
//        return "当前调用端口为 " + serviceInstance.getPort() + "的服务，返回结果为 " + s;
//    }

    @GetMapping("/index")
    public String index(){
        return restTemplate.getForObject("http://provider/provider/index", String.class);
    }
}
